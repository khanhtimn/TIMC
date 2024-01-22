package com.teamti.timc.modules;

import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import org.lwjgl.input.Keyboard;

public class ItemPhysics extends Module {
    public ItemPhysics() {
        super("ItemPhysics", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(4);
    }
}
