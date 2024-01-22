package com.teamti.timc.modules;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import com.teamti.timc.util.BooleanValue;
import com.teamti.timc.util.TimerUtil;

public class MenuGUI extends Module {

	public Random rand = new Random();
	public BooleanValue blur;
	public TimerUtil timer = new TimerUtil();
	
	public MenuGUI() {
        super("Menu", Keyboard.KEY_RSHIFT);
        blur = new BooleanValue("Blur", true);
        addValue(blur);
    }
   
	public void onEnable(){
		if (mc.theWorld != null) {
             this.mc.displayGuiScreen(TIMC.jgui);
            this.toggle();
        }

	}
	
	
   
	
}
