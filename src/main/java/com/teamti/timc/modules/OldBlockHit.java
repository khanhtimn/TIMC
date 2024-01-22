package com.teamti.timc.modules;

import com.teamti.timc.event.EventManager;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import org.lwjgl.input.Keyboard;

public class OldBlockHit extends Module {

    public static boolean isEnabled;

	public OldBlockHit() {
        super("1.7 BlockHit", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(1);
    }

    public void onEnable(){
        isEnabled = true;
        EventManager.register(this);
    }

    public void onDisable(){
        EventManager.unregister(this);
        isEnabled = false;
    }
	
}
