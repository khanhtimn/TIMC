package com.teamti.timc.event.events;

import com.teamti.timc.event.events.callables.EventCancellable;

public class EventJoinServer
extends EventCancellable {
    String ip;

    public EventJoinServer(String serverIP) {
        this.ip = serverIP;
    }

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

    
}

