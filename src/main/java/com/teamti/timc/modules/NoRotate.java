package com.teamti.timc.modules;

import com.teamti.timc.event.EventManager;
import com.teamti.timc.event.EventTarget;
import com.teamti.timc.event.events.EventReceivePacket;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.lwjgl.input.Keyboard;


public class NoRotate extends Module {

    public static boolean isEnabled;

    public NoRotate() {
        super("NoRotate", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(0);
    }

    public void onEnable() {
        isEnabled = true;
        EventManager.register(this);
    }

    public void onDisable() {
        EventManager.unregister(this);
        isEnabled = false;
    }


    @EventTarget
    public void onReceive(EventReceivePacket event){
        if(event.getPacket() instanceof S08PacketPlayerPosLook){
            S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event.getPacket();
            packet.field_148936_d = mc.thePlayer.rotationYaw;
            packet.field_148937_e = mc.thePlayer.rotationPitch;
        }
    }
}

