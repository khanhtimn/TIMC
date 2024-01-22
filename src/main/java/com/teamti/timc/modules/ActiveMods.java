package com.teamti.timc.modules;

import org.lwjgl.input.Keyboard;

import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;

public class ActiveMods extends Module {

	
	public static boolean enabled;

	public ActiveMods() {
        super("ActiveMods", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(4);
    }
   
	public void onEnable(){
		enabled = true;
		
	}
	
	public void onDisable(){
		enabled = false;
	}
	
}
