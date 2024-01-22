package com.teamti.timc.modules;

import com.teamti.timc.event.EventManager;
import com.teamti.timc.event.EventTarget;
import com.teamti.timc.event.events.EventReceivePacket;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import com.teamti.timc.util.NumberValue;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import org.lwjgl.input.Keyboard;

public class TimeChanger extends Module {

    public static NumberValue time;
    public static boolean isEnabled;

	public TimeChanger() {
        super("TimeChanger", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(4);
        time = new NumberValue("Time", 16000.0D, 0.0D, 24000.0D, 250.0D);
        addValue(time);
    }

    public void onEnable(){
        isEnabled = true;
        EventManager.register(this);
    }

    public void onDisable(){
        EventManager.unregister(this);
        isEnabled = false;
    }

    @EventTarget
    private void onPacketRecieve(final EventReceivePacket event) {
        if (event.getPacket() instanceof S03PacketTimeUpdate) {
            event.setCancelled(true);
        }
    }
	
}
