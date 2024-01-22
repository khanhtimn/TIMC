package com.teamti.timc.modules;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import com.teamti.timc.util.TimerUtil;

public class ClickGUI extends Module {

	public Random rand = new Random();
	
	public TimerUtil timer = new TimerUtil();
	
	public ClickGUI() {
        super("ClickGUI", Keyboard.KEY_RSHIFT);
    }
   
	public void onEnable(){
		if (mc.theWorld != null) {
             this.mc.displayGuiScreen(TIMC.jgui);
            this.setToggled(false);
        }

	}
	
	public void onDisable(){
		
	}
	
    
	
	
}
