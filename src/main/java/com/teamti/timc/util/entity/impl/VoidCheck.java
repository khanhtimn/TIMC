package com.teamti.timc.util.entity.impl;

import com.teamti.timc.util.entity.ICheck;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;

public final class VoidCheck implements ICheck {
   public boolean validate(Entity entity) {
      return this.isBlockUnder(entity);
   }

   private boolean isBlockUnder(Entity entity) {
      for(int offset = 0; (double)offset < entity.posY + (double)entity.getEyeHeight(); offset += 2) {
         AxisAlignedBB boundingBox = entity.getEntityBoundingBox().offset(0.0D, (double)(-offset), 0.0D);
         if (!Minecraft.getMinecraft().theWorld.getCollidingBoundingBoxes(entity, boundingBox).isEmpty()) {
            return true;
         }
      }

      return false;
   }
}
