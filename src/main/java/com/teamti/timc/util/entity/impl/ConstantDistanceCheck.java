package com.teamti.timc.util.entity.impl;

import com.teamti.timc.util.entity.ICheck;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public final class ConstantDistanceCheck implements ICheck {
   private final float distance;

   public ConstantDistanceCheck(float distance) {
      this.distance = distance;
   }

   public boolean validate(Entity entity) {
      return Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) <= this.distance;
   }
}
