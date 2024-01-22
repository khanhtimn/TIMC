package com.teamti.timc.modules;

import com.teamti.timc.event.EventManager;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class ThirdPerson extends Module {

    public static boolean isEnabled;

	public ThirdPerson() {
        super("ThirdPerson", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(4);
    }
    public void onEnable(){
        isEnabled = true;
        Minecraft.getMinecraft().gameSettings.thirdPersonView = 3;
        EventManager.register(this);
    }

    public void onDisable(){
        EventManager.unregister(this);
        Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
        isEnabled = false;
    }
	
	
	
}
