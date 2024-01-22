package com.teamti.timc.event.events;

import com.teamti.timc.event.events.callables.EventCancellable;

public class EventPushOutOfBlock
extends EventCancellable {
    private boolean cancel;

    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    @Override
    public void setCancelled(boolean state) {
        this.cancel = state;
    }
}

