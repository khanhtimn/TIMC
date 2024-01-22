package com.teamti.timc.event.events;

import com.teamti.timc.event.events.callables.EventCancellable;

public class EventClick
extends EventCancellable {
    private boolean canceled;

    @Override
    public boolean isCancelled() {
        return this.canceled;
    }

    @Override
    public void setCancelled(boolean state) {
        this.canceled = state;
    }
}

