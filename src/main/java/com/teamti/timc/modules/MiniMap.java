package com.teamti.timc.modules;

import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import org.lwjgl.input.Keyboard;

public class MiniMap extends Module {

	
	public MiniMap() {
        super("MiniMap", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(4);
    }
}
