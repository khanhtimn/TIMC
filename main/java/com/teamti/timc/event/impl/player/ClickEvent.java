package com.teamti.timc.event.impl.player;

import com.teamti.timc.event.Event;

public class ClickEvent extends Event
{
    boolean fake;

    public ClickEvent(boolean fake) { this.fake = fake; }

    public boolean isFake() { return fake; }
}
