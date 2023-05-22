package com.teamti.timc.event.impl.player;

import com.teamti.timc.event.Event;


public class PlayerSendMessageEvent extends Event {
    private final String message;

    public PlayerSendMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
