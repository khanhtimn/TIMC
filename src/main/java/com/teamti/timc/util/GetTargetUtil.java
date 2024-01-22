package com.teamti.timc.util;

import com.teamti.timc.event.EventManager;
import com.teamti.timc.event.EventTarget;
import com.teamti.timc.event.events.EventMotion;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWaterMob;

import java.util.ArrayList;
import java.util.List;

public class GetTargetUtil {

    private int targetIndex;
    public EntityLivingBase target;
    public static boolean isEnabled;
    private List<EntityLivingBase> targetList = new ArrayList<>();
    Minecraft mc = Minecraft.getMinecraft();

    public void onEnable() {
        isEnabled = true;
        EventManager.register(this);
        targetList.clear();
        target = null;
    }

    public void onDisable() {
        targetList.clear();
        target = null;
        EventManager.unregister(this);
        isEnabled = false;
    }

    @EventTarget
    public void onEvent(EventMotion eventMotion) {

        collectTargets();

        if (targetIndex >= targetList.size())
            targetIndex = 0;

        target = !targetList.isEmpty() &&
                targetIndex < targetList.size() ?
                targetList.get(targetIndex) :
                null;
    }

    private void collectTargets() {
        targetList.clear();

        for (Object entity : mc.thePlayer.getEntityWorld().loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
                if (isValid(entityLivingBase, true))
                    targetList.add(entityLivingBase);
            }
        }
    }

    private boolean isValid(EntityLivingBase entityLivingBase, boolean blocking) {
        if (entityLivingBase == mc.thePlayer || entityLivingBase.isDead /*|| entityLivingBase.getDistanceToEntity(mc.thePlayer) > (blocking ? blockRange.getValue() : range.getValue())*/ || entityLivingBase.getHealth() == 0 || entityLivingBase.isInvisible() || entityLivingBase instanceof EntityArmorStand)
            return false;

        if (!entityLivingBase.canEntityBeSeen(mc.thePlayer))
            return false;

        /*
        if (entityLivingBase instanceof EntityPlayer && !entities.getSelectedObjects().contains("Players"))
            return false;
         */

        if ((entityLivingBase instanceof EntityMob || entityLivingBase instanceof EntityAmbientCreature || entityLivingBase instanceof EntityWaterMob) /*&&!entities.getSelectedObjects().contains("Mobs")*/)
            return false;

        if (entityLivingBase instanceof EntityAnimal /*&& !entities.getSelectedObjects().contains("Animals")*/)
            return false;

        if (entityLivingBase instanceof EntityGolem /*&& !entities.getSelectedObjects().contains("Golems")*/)
            return false;

        return !(entityLivingBase instanceof EntityVillager) /*|| entities.getSelectedObjects().contains("Villagers")*/;
    }

    /*
    public final List targets;
    public int targetIndex;
    private final EntityValidator entityValidator;

    public GetTargetUtil() {
        this.targets = new ArrayList();
        entityValidator = new EntityValidator();
    }

    public final EntityLivingBase getTarget() {
        if (this.targets.isEmpty()) {
            return null;
        } else {
            int size = this.targets.size();
            if (size >= this.targetIndex) {
                ++this.targetIndex;
            }

            if (size <= this.targetIndex) {
                this.targetIndex = 0;
            }

            return (EntityLivingBase)this.targets.get(this.targetIndex);
        }
    }

    public final void updateTargets() {
        this.targets.clear();
        List entities = Minecraft.getMinecraft().theWorld.loadedEntityList;
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

     */
}
