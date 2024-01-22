package com.teamti.timc.modules;

import org.lwjgl.input.Keyboard;

import com.teamti.timc.main.Module;

public class Notifications extends Module {

	public static boolean enabled;
	
	public Notifications() {
        super("Notifications", Keyboard.KEY_NONE);
    }
   
	public void onEnable(){
		enabled = true;
	}
	
	public void onDisable(){
		enabled = false;
	}
	
	
}
