package com.teamti.timc.modules;

import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import org.lwjgl.input.Keyboard;

public class ChunkAnimation extends Module {

    public ChunkAnimation() {
        super("ChunkAnimation", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(4);
    }
}