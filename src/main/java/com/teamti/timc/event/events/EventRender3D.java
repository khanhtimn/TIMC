package com.teamti.timc.event.events;

import com.teamti.timc.event.events.callables.EventCancellable;

public class EventRender3D
extends EventCancellable {
    public static float partialTicks;

    public EventRender3D(float partialTicks) {
        EventRender3D.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}

