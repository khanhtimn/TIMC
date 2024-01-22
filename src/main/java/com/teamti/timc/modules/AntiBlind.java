package com.teamti.timc.modules;

import com.teamti.timc.event.EventManager;
import com.teamti.timc.event.EventTarget;
import com.teamti.timc.event.events.EventPreMotionUpdates;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import org.lwjgl.input.Keyboard;


public class AntiBlind extends Module {

    public static boolean isEnabled;

    public AntiBlind() {
        super("AntiBlind", Keyboard.KEY_NONE);
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
    public void onEvent(EventPreMotionUpdates event){
        Minecraft.getMinecraft().thePlayer.removePotionEffect(Potion.confusion.getId());
        Minecraft.getMinecraft().thePlayer.removePotionEffect(Potion.blindness.getId());
    }
}

