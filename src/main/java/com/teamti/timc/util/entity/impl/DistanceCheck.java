package com.teamti.timc.util.entity.impl;

import com.teamti.timc.util.NumberValue;
import com.teamti.timc.util.entity.ICheck;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;


public final class DistanceCheck implements ICheck {
   private final NumberValue distance;

   public DistanceCheck(NumberValue distance) {
      this.distance = distance;
   }

   public boolean validate(Entity entity) {
      return (double)Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) <= (Double)this.distance.getValue();
   }
}
