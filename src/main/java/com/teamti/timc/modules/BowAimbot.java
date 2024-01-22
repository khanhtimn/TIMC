package com.teamti.timc.modules;

import com.teamti.timc.event.EventManager;
import com.teamti.timc.event.EventTarget;
import com.teamti.timc.event.events.EventPreMotionUpdates;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import com.teamti.timc.util.BooleanValue;
import com.teamti.timc.util.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class BowAimbot extends Module {


    public static boolean isEnabled;
    public NumberValue reach = new NumberValue("Reach", 32, 6, 256,3);
    //public BooleanValue lockView = new BooleanValue("Lockview", true);
    public BooleanValue monsters = new BooleanValue("Monsters", false);
    public BooleanValue players = new BooleanValue("Players", true);
    public BooleanValue animals = new BooleanValue("Animals", false);


    //private Property<TargetingMode> tMode = new Property<TargetingMode>(this, "Target", TargetingMode.Health);
    public static ArrayList<Entity> attackList = new ArrayList<>();
    public static ArrayList<Entity> targets = new ArrayList<>();
    public static int currentTarget;

    public BowAimbot() {
        super("BowAimbot", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(0);
        addValue(reach);
        addValue(monsters);
        addValue(players);
        addValue(animals);

    }



    public void onEnable(){
        isEnabled = true;
        EventManager.register(this);
    }

    public void onDisable()
    {
        targets.clear();
        attackList.clear();
        currentTarget = 0;
        EventManager.unregister(this);
        isEnabled = false;
    }

    //private enum TargetingMode {
    //    Distance, Health;
    //}

    public boolean isValidTarget(final Entity entity) {
        boolean valid = false;
        if (entity == mc.thePlayer.ridingEntity)
            return false;

        if (entity.isInvisible() || entity instanceof EntityArmorStand)
            valid = false;

        //if (FriendManager.isFriend(entity.getName()) && entity instanceof EntityPlayer || !mc.thePlayer.canEntityBeSeen(entity))
        //    return false;

        if (entity instanceof EntityPlayer && players.getValue()) {
            valid = (entity != null && mc.thePlayer.getDistanceToEntity(entity) <= reach.getValue() && entity != mc.thePlayer && entity.isEntityAlive() /*&& !FriendManager.isFriend(entity.getName())*/);
            if (entity.isInvisible())
                valid = false;
        } else if (entity instanceof IMob && monsters.getValue()) {
            valid = (entity != null && mc.thePlayer.getDistanceToEntity(entity) <= reach.getValue() && entity.isEntityAlive());
        } else if (entity instanceof IAnimals && !(entity instanceof IMob) && animals.getValue()) {
            valid = (entity != null && mc.thePlayer.getDistanceToEntity(entity) <= reach.getValue() && entity.isEntityAlive());
        }
        return valid;
    }

    @EventTarget
    public void onPre(EventPreMotionUpdates pre) {
        setDisplayName("BowAimbot");
        setDisplayName(getDisplayName() + " [" + reach.getValue() + " Blocks]");
        for (Object o : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            Entity e = (Entity) o;


            if (e.isInvisible() || e instanceof EntityArmorStand) {
                targets.remove(e);
            }


            if ((animals.getValue()) && ((e instanceof EntityAnimal)) && (!targets.contains(e))) {
                targets.add(e);
            }
            if ((players.getValue()) && ((e instanceof EntityPlayer)) && (!targets.contains(e))) {
                targets.add(e);
            }
            if ((monsters.getValue()) && ((e instanceof EntityMob)) && (!targets.contains(e))) {
                targets.add(e);
            }
            if ((!animals.getValue()) && (targets.contains(e)) && ((e instanceof EntityAnimal))) {
                targets.remove(e);
            }
            if ((!players.getValue()) && (targets.contains(e)) && ((e instanceof EntityPlayer))) {
                targets.remove(e);
            }
            if ((!monsters.getValue()) && (targets.contains(e)) && ((e instanceof EntityMob))) {
                targets.remove(e);
            }
        }
        if (currentTarget >= attackList.size())
            currentTarget = 0;

        for (Object o : mc.theWorld.loadedEntityList) {
            Entity e = (Entity) o;
            if ((isValidTarget(e)) && (!attackList.contains(e)))
                attackList.add(e);
        }

        for (Object o : attackList) {
            Entity e = (Entity) o;
            if (!isValidTarget(e) && attackList.contains(e))
                attackList.remove(e);
        }

        sortTargets();
        if (mc.thePlayer != null && attackList.get(currentTarget) != null && isValidTarget(attackList.get(currentTarget)) && mc.thePlayer.isUsingItem() && mc.thePlayer.getCurrentEquippedItem().getItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow) {
            int bowCurrentCharge = mc.thePlayer.getItemInUseDuration();
            float bowVelocity = (bowCurrentCharge / 20.0f);
            bowVelocity = (bowVelocity * bowVelocity + bowVelocity * 2.0f) / 3.0f;
            bowVelocity = MathHelper.clamp_float(bowVelocity, 0.0F, 1.0F);

            double v = bowVelocity * 3.0F;
            double g = 0.05000000074505806D;

            if (bowVelocity < 0.1)
                return;

            if (bowVelocity > 1.0f)
                bowVelocity = 1.0f;

            final double xDistance = attackList.get(currentTarget).posX - mc.thePlayer.posX + (attackList.get(currentTarget).posX - attackList.get(currentTarget).lastTickPosX) * ((float) (bowVelocity) * 10.0f);
            final double zDistance = attackList.get(currentTarget).posZ - mc.thePlayer.posZ + (attackList.get(currentTarget).posZ - attackList.get(currentTarget).lastTickPosZ) * ((float) (bowVelocity) * 10.0f);
            final double trajectoryXZ = Math.sqrt(xDistance * xDistance + zDistance * zDistance);
            final float trajectoryTheta90 = (float) (Math.atan2(zDistance, xDistance) * 180.0 / 3.141592653589793) - 90.0f;
            final float bowTrajectory = (float) -Math.toDegrees(getLaunchAngle((EntityLivingBase) attackList.get(currentTarget), v, g)) - 3;
            if (trajectoryTheta90 <= 360 && bowTrajectory <= 360) {

                //if(lockView){
                mc.thePlayer.rotationYaw = trajectoryTheta90;
                mc.thePlayer.rotationPitch = bowTrajectory;
                //}
                    /*
                    else {
                        pre.setYaw(trajectoryTheta90);
                        pre.setPitch(bowTrajectory);
                    }
                     */
            }
        }
    }

    public void sortTargets() {
        //switch (tMode.value) {
        //    case Distance:
        attackList.sort((ent1, ent2) -> {
            double d1 = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(ent1);
            double d2 = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(ent2);
            return (d1 < d2) ? -1 : (d1 == d2) ? 0 : 1;
        });
        //        ;
        //        break;
        //    case Health:
        //        attackList.sort((ent1, ent2) -> {
        //            double h1 = ((EntityLivingBase) ent1).getHealth();
        //            double h2 = ((EntityLivingBase) ent2).getHealth();
        //            return (h1 < h2) ? -1 : (h1 == h2) ? 0 : 1;
        //        });
        //        ;
        //        break;
        //}
    }

    private float getLaunchAngle(EntityLivingBase targetEntity, double v, double g) {
        double yDif = targetEntity.posY + targetEntity.getEyeHeight() / 2.0F - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        double xDif = targetEntity.posX - Minecraft.getMinecraft().thePlayer.posX;
        double zDif = targetEntity.posZ - Minecraft.getMinecraft().thePlayer.posZ;

        double xCoord = Math.sqrt(xDif * xDif + zDif * zDif);

        return theta(v + 2, g, xCoord, yDif);
    }

    private float theta(double v, double g, double x, double y) {
        double yv = 2.0D * y * (v * v);
        double gx = g * (x * x);
        double g2 = g * (gx + yv);
        double insqrt = v * v * v * v - g2;
        double sqrt = Math.sqrt(insqrt);

        double numerator = v * v + sqrt;
        double numerator2 = v * v - sqrt;

        double atan1 = Math.atan2(numerator, g * x);
        double atan2 = Math.atan2(numerator2, g * x);

        return (float) Math.min(atan1, atan2);
    }

}