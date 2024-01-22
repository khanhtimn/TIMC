package com.teamti.timc.event.events;

import com.teamti.timc.event.events.callables.EventCancellable;

import net.minecraft.entity.EntityLivingBase;

public class EventRenderNameTag
extends EventCancellable {
   EntityLivingBase e;
	public EventRenderNameTag(EntityLivingBase e){
		this.e = e;
	}
	public EntityLivingBase getEntity() {
		return e;
	}
	public void setEntity(EntityLivingBase e) {
		this.e = e;
	}
    
	
	
}

