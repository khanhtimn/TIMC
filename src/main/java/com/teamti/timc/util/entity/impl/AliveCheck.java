package com.teamti.timc.util.entity.impl;

import com.teamti.timc.util.entity.ICheck;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public final class AliveCheck implements ICheck {
   public boolean validate(Entity entity) {
      return entity.isEntityAlive() || Minecraft.getMinecraft().getCurrentServerData().serverIP.contains("mineplex");
   }
}
