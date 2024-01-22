package com.teamti.timc.event.events;

import com.teamti.timc.event.events.callables.EventCancellable;

public class EventReach
extends EventCancellable {
	private float range;
    
    public EventReach(final float f) {
        this.range = f;
    }
    
    public float getRange() {
        return this.range;
    }
    
    public void setRange(final float range) {
        this.range = range;
    }
}

