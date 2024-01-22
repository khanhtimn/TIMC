package com.teamti.timc.event.events;

import com.teamti.timc.event.events.callables.EventCancellable;
import net.minecraft.client.gui.ScaledResolution;

public class EventRenderGUI extends EventCancellable {
    private final ScaledResolution sr;

    public EventRenderGUI(ScaledResolution sr) {
        this.sr = sr;
    }

    public ScaledResolution getScaledRes() {
        return this.sr;
    }
}
