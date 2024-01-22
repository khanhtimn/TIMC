package com.teamti.timc.event.events;

import com.teamti.timc.event.events.callables.EventCancellable;

import net.minecraft.network.Packet;

public class EventReceivePacket extends EventCancellable {

	public Packet packet;
	
	public Packet getPacket() {
		return this.packet;
	}
	
	public void setPacket(Packet packet) {
		this.packet = packet;
		
	}
	
}
