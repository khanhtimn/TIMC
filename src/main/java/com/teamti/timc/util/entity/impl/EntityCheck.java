package com.teamti.timc.util.entity.impl;

import com.teamti.timc.util.BooleanValue;
import com.teamti.timc.util.entity.ICheck;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;


public final class EntityCheck implements ICheck {
   private final BooleanValue players;
   private final BooleanValue animals;
   private final BooleanValue monsters;
   private final BooleanValue invisibles;
   //private final Supplier friend;

   public EntityCheck(BooleanValue players, BooleanValue animals, BooleanValue monsters, BooleanValue invisibles /*,Supplier friend*/) {
      this.players = players;
      this.animals = animals;
      this.monsters = monsters;
      this.invisibles = invisibles;
      //this.friend = friend;
   }

   public boolean validate(Entity entity) {
      if (entity instanceof EntityPlayerSP) {
         return false;
      } else if (!this.invisibles.getValue() && entity.isInvisible()) {
         return false;
      } else if (this.animals.getValue() && entity instanceof EntityAnimal) {
         return true;
         /*
      } else if (this.players.getValue() && entity instanceof EntityPlayer) {
         return !FriendManager.isFriend(entity.getName()) || !(Boolean)this.friend.get();
         */
      } else {
         return this.monsters.getValue() && (entity instanceof EntityMob || entity instanceof EntitySlime || entity instanceof EntityDragon || entity instanceof EntityGolem);
      }
   }
}
