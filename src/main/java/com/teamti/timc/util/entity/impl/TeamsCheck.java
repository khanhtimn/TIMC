package com.teamti.timc.util.entity.impl;

import com.teamti.timc.util.BooleanValue;
import com.teamti.timc.util.PlayerUtil;
import com.teamti.timc.util.entity.ICheck;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;


public final class TeamsCheck implements ICheck {
   private final BooleanValue teams;

   public TeamsCheck(BooleanValue teams) {
      this.teams = teams;
   }

   public boolean validate(Entity entity) {
      return !(entity instanceof EntityPlayer) || !PlayerUtil.isOnSameTeam((EntityPlayer)entity) || !this.teams.getValue();
   }
}
