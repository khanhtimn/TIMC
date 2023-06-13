package com.teamti.timc.event.impl.game;

import com.teamti.timc.event.Event;


public class TickEvent extends Event.StateEvent {

    private final int ticks;

    public TickEvent(int ticks) {
        this.ticks = ticks;
    }

    public int getTicks() {
        return ticks;
    }

}
