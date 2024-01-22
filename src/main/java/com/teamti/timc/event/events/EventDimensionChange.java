package com.teamti.timc.event.events;

import com.teamti.timc.event.events.callables.EventCancellable;

public class EventDimensionChange
extends EventCancellable {
    int dimension;

    public EventDimensionChange(int dimension) {
        this.dimension = dimension;
    }

    public int getDimension() {
        return this.dimension;
    }
}

