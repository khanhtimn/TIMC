package com.teamti.timc.modules;

import com.teamti.timc.event.EventManager;
import com.teamti.timc.event.EventTarget;
import com.teamti.timc.event.events.EventMotion;
import com.teamti.timc.event.types.EventType2;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import com.teamti.timc.util.BooleanValue;
import com.teamti.timc.util.NumberValue;
import com.teamti.timc.util.RandomUtil;
import com.teamti.timc.util.TimerUtil;
import com.teamti.timc.util.entity.EntityValidator;
import com.teamti.timc.util.entity.impl.*;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public final class AuraMod extends Module {
   public static boolean blocking;
   private static final Random RANDOM = new Random();
   public final BooleanValue autoBlock = new BooleanValue("Auto Block", true);
   public final NumberValue aps;
   //public final NumberValue randomization = new DoubleOption("Randomization", 0.0D, 0.0D, 5.0D, 1.0D);
   //public final EnumOption mode;
   //public final EnumOption autoBlockMode;
   public final NumberValue switchDelay;
   //public final EnumOption sortingMode;
   public final NumberValue range;
   public final BooleanValue teams;
   public final BooleanValue players;
   public final BooleanValue animals;
   public final BooleanValue monsters;
   public final BooleanValue prioritizePlayers;
   public final BooleanValue invisibles;
   public final BooleanValue forceUpdate;
   public final BooleanValue disableOnDeath;
   private final List targets;
   private final TimerUtil attackStopwatch;
   private final TimerUtil updateStopwatch;
   private final TimerUtil critStopwatch;
   private final EntityValidator entityValidator;
   private final EntityValidator blockValidator;
   private final double[] hypixelOffsets;
   private final double[] offsets;
   private int targetIndex;
   private boolean changeTarget;

   public AuraMod() {
      super("AuraMod", Keyboard.KEY_NONE);
      this.jelloCat = TIMC.tabgui.cats.get(2);
      //this.mode = new EnumOption("Mode", AuraMod.Mode.SWITCH);
      //this.autoBlockMode = new EnumOption("Auto Block Mode", AuraMod.AutoBlockMode.OFFSET);
      switchDelay = new NumberValue("Switch Delay", 3, 1.0, 10, 1);
      addValue(switchDelay);
      aps = new NumberValue("APS", 13, 1.0, 20, 1);
      addValue(aps);

      //this.sortingMode = new EnumOption("Sorting Mode", AuraMod.SortingMode.DISTANCE, () -> {
      //   return this.mode.getValue() == AuraMod.Mode.SINGLE;
      //});
      //this.range = new DoubleOption("Range", 4.2D, 3.0D, 7.0D, 0.1D);
      range = new NumberValue("Reach", 3.6, 3, 7.0, 0.1);
      addValue(range);
      
      this.teams = new BooleanValue("Teams", true);
      addValue(teams);
      this.players = new BooleanValue("Players", true);
      addValue(players);
      this.animals = new BooleanValue("Animals", true);
      addValue(animals);
      this.monsters = new BooleanValue("Monsters", true);
      addValue(monsters);

      this.prioritizePlayers = new BooleanValue("prioritizePlayers", true);
      addValue(prioritizePlayers);

      //this.prioritizePlayers = new BooleanValue("Prioritize Players", true, () -> {
      //   return (this.animals.getValue() || this.monsters.getValue()) && this.players.getValue();
      //});
      this.invisibles = new BooleanValue("Invisibles", false);
      addValue(invisibles);
      this.forceUpdate = new BooleanValue("Force Update", true);
      addValue(forceUpdate);
      this.disableOnDeath = new BooleanValue("Disable on death", true);
      addValue(disableOnDeath);
      this.targets = new ArrayList();
      this.attackStopwatch = new TimerUtil();
      this.updateStopwatch = new TimerUtil();
      this.critStopwatch = new TimerUtil();
      this.entityValidator = new EntityValidator();
      this.blockValidator = new EntityValidator();
      this.hypixelOffsets = new double[]{0.05000000074505806D, 0.0015999999595806003D, 0.029999999329447746D, 0.0015999999595806003D};
      this.offsets = new double[]{0.05D, 0.0D, 0.012511D, 0.0D};
      //this.setMode(this.mode);
      //this.addOptions(new Option[]{this.mode, this.sortingMode, this.autoBlockMode, this.aps, this.randomization, this.range, this.switchDelay, this.teams, this.players, this.prioritizePlayers, this.animals, this.monsters, this.invisibles, this.autoBlock, this.forceUpdate, this.disableOnDeath});
      AliveCheck aliveCheck = new AliveCheck();
      EntityCheck entityCheck = new EntityCheck(this.players, this.animals, this.monsters, this.invisibles);
      TeamsCheck teamsCheck = new TeamsCheck(this.teams);
      this.entityValidator.add(aliveCheck);
      this.entityValidator.add(new DistanceCheck(this.range));
      this.entityValidator.add(entityCheck);
      this.entityValidator.add(teamsCheck);
      this.blockValidator.add(aliveCheck);
      this.blockValidator.add(new ConstantDistanceCheck(8.0F));
      this.blockValidator.add(entityCheck);
      this.blockValidator.add(teamsCheck);

   }

   public void onDisable() {
      this.unblock();
      EventManager.unregister(this);
   }

   public void onEnable() {
      EventManager.register(this);
      this.updateStopwatch.reset();
      this.critStopwatch.reset();
      this.targetIndex = 0;
      this.targets.clear();
      this.changeTarget = false;
   }

   @EventTarget
   public void onMotion(EventMotion event) {
      this.updateTargets();
      this.sortTargets();
      if (!(mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)) {
         blocking = false;
      }

      EntityLivingBase target = this.getTarget();
      if (target == null) {
         this.unblock();
      }

      if (event.getType() == EventType2.PRE && target != null) {
         if (this.updateStopwatch.hasTimeElapsed((56L),false) && this.forceUpdate.getValue() && !mc.thePlayer.isMoving()) {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.onGround));
            this.updateStopwatch.reset();
         }

         float[] angles = this.getRotationsEntity(target);
         event.setYaw(angles[0]);
         event.setPitch(angles[1]);
      }

   }

   @EventTarget
   public final void onTick() {
      EntityLivingBase target = this.getTarget();
      if (target != null) {
         if (this.attackStopwatch.hasTimeElapsed((long)(1000 / ((Double)this.aps.getValue()).intValue()),false)) {
            this.attack(target);
            this.attackStopwatch.reset();
         }

         if ((double)target.hurtTime >= (Double)this.switchDelay.getValue()) {
            this.changeTarget = true;
         }
      }

      this.block();
   }

   public final EntityLivingBase getTarget() {
      if (this.targets.isEmpty()) {
         return null;
      } else /*if(this.mode.getValue() == AuraMod.Mode.SINGLE)*/ {
         return (EntityLivingBase)this.targets.get(0);
      }
      /*
      else {
         int size = this.targets.size();
         if (size >= this.targetIndex && this.changeTarget) {
            ++this.targetIndex;
            this.changeTarget = false;
         }

         if (size <= this.targetIndex) {
            this.targetIndex = 0;
         }

         return (EntityLivingBase)this.targets.get(this.targetIndex);
      }
       */
   }

   private boolean isEntityNearby() {
      List loadedEntityList = mc.theWorld.loadedEntityList;
      int i = 0;

      for(int loadedEntityListSize = loadedEntityList.size(); i < loadedEntityListSize; ++i) {
         Entity entity = (Entity)loadedEntityList.get(i);
         if (this.blockValidator.validate(entity)) {
            return true;
         }
      }

      return false;
   }

   private void attack(EntityLivingBase entity) {
      EntityPlayerSP player = mc.thePlayer;
      NetHandlerPlayClient netHandler = mc.getNetHandler();
      boolean shouldCritical = player.isCollidedVertically && player.onGround && this.critStopwatch.hasTimeElapsed((200L),false) && entity.hurtTime <= 0;
      if (shouldCritical) {
         double[] var7 = this.hypixelOffsets;
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            double offset = var7[var9];
            netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY + offset, player.posZ, false));
         }

         this.critStopwatch.reset();
      }

      this.unblock();
      player.swingItem();
      netHandler.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
      //if (this.autoBlockMode.getValue() == AuraMod.AutoBlockMode.OFFSET) {
      //   mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
      //}

   }

   private void unblock() {
      if ((this.autoBlock.getValue() || mc.thePlayer.isBlocking()) && (mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)) {
         //switch((AuraMod.AutoBlockMode)this.autoBlockMode.getValue()) {
         //case OFFSET:
         //case SMART:
            if (blocking) {
               mc.playerController.syncCurrentPlayItem();
               mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
               blocking = false;
            }
         }
   }


   private void block() {
      if (this.autoBlock.getValue() && (mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) && this.isEntityNearby()) {
         //switch((AuraMod.AutoBlockMode)this.autoBlockMode.getValue()) {
         //case OFFSET:
         //   mc.playerController.sendBlockSword(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
         //    blocking = true;
         //   break;
         //case SMART:
            if (!blocking) {
               mc.playerController.syncCurrentPlayItem();
               mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
               blocking = true;
            }
         }
   }


   //private boolean canAttack() {
   //   return BlockFly.isEnabled = false;
   //}

   private void updateTargets() {
      this.targets.clear();
      List entities = mc.theWorld.loadedEntityList;
      int i = 0;

      for(int entitiesSize = entities.size(); i < entitiesSize; ++i) {
         Entity entity = (Entity)entities.get(i);
         if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            if (this.entityValidator.validate(entityLivingBase)) {
               this.targets.add(entityLivingBase);
            }
         }
      }

   }

   private void sortTargets() {
      //switch((AuraMod.SortingMode)this.sortingMode.getValue()) {
      //case HEALTH:
      //   this.targets.sort(Comparator.comparingDouble(EntityLivingBase::getHealth));
      //  break;
      //case DISTANCE:
         this.targets.sort(Comparator.comparingDouble((entity) -> {
            return (double)mc.thePlayer.getDistanceToEntity((Entity) entity);
         }));
      //}

      if (this.prioritizePlayers.getValue()) {
         this.targets.sort(Comparator.comparing((entity) -> {
            return entity instanceof EntityPlayer;
         }));
      }

   }

   private static enum SortingMode {
      DISTANCE,
      HEALTH;
   }

   private static enum AutoBlockMode {
      SMART,
      OFFSET;
   }

   private static enum Mode {
      SINGLE,
      SWITCH;
   }

   public static float[] getRotations(double posX, double posY, double posZ) {
      EntityPlayerSP player = mc.thePlayer;
      double x = posX - player.posX;
      double y = posY - (player.posY + (double)player.getEyeHeight());
      double z = posZ - player.posZ;
      double dist = (double) MathHelper.sqrt_double(x * x + z * z);
      float yaw = (float)(Math.atan2(z, x) * 180.0D / 3.141592653589793D) - 90.0F;
      float pitch = (float)(-(Math.atan2(y, dist) * 180.0D / 3.141592653589793D));
      return new float[]{yaw, pitch};
   }

   public static float[] getRotationsEntity(EntityLivingBase entity) {
      return mc.thePlayer.isMoving() ? getRotations(entity.posX + RandomUtil.randomNumber(0.03D, -0.03D), entity.posY + (double)entity.getEyeHeight() - 0.4D + RandomUtil.randomNumber(0.07D, -0.07D), entity.posZ + RandomUtil.randomNumber(0.03D, -0.03D)) : getRotations(entity.posX, entity.posY + (double)entity.getEyeHeight() - 0.4D, entity.posZ);
   }

}