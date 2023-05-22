package com.teamti.timc.event.impl.game;

import com.teamti.timc.event.Event;
import net.minecraft.client.gui.GuiScreen;

public class GuiOpenEvent extends Event.StateEvent {
    public GuiScreen gui;
    public GuiOpenEvent(GuiScreen gui)
    {
        this.gui = gui;
    }
}
