package com.teamti.timc.event.impl.player;

import com.teamti.timc.event.Event;
import lombok.AllArgsConstructor;
import net.minecraft.entity.EntityLivingBase;


@AllArgsConstructor
public class AttackEvent extends Event {

    private final EntityLivingBase targetEntity;

    public EntityLivingBase getTargetEntity() {
        return targetEntity;
    }

}
