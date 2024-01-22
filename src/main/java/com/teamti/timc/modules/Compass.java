package com.teamti.timc.modules;

import org.lwjgl.input.Keyboard;

import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;

public class Compass extends Module {

	
	public Compass() {
        super("Compass", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(4);
    }
   
	
	
	
}
