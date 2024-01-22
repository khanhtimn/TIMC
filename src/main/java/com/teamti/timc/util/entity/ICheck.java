package com.teamti.timc.util.entity;

import net.minecraft.entity.Entity;

@FunctionalInterface
public interface ICheck {
   boolean validate(Entity var1);
}
