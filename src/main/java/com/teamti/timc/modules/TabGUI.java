package com.teamti.timc.modules;

import org.lwjgl.input.Keyboard;

import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;

public class TabGUI extends Module {

	
	public TabGUI() {
        super("TabGUI", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(4);
    }
   
	
	
	
}
