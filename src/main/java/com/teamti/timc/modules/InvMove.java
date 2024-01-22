package com.teamti.timc.modules;

import com.teamti.timc.event.EventManager;
import com.teamti.timc.event.EventTarget;
import com.teamti.timc.event.events.EventPreMotionUpdates;
import com.teamti.timc.clickgui.TIMCGui;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.util.Objects;

public class InvMove extends Module {

    public InvMove() {
        super("InvMove", Keyboard.KEY_H);
        this.jelloCat = TIMC.tabgui.cats.get(0);
    }


    public void onEnable() {
        EventManager.register(this);
    }

    public void onDisable() {
        EventManager.unregister(this);
    }

    @EventTarget
    public void onEvent(EventPreMotionUpdates event) {
        KeyBinding[] keys = {Minecraft.getMinecraft().gameSettings.keyBindRight, Minecraft.getMinecraft().gameSettings.keyBindLeft, Minecraft.getMinecraft().gameSettings.keyBindBack, Minecraft.getMinecraft().gameSettings.keyBindForward, Minecraft.getMinecraft().gameSettings.keyBindJump, Minecraft.getMinecraft().gameSettings.keyBindSprint };
        KeyBinding[] arrayOfKeyBinding1;
        int nignog;
        int hereInMyGarage;
        if ((Minecraft.getMinecraft().currentScreen instanceof GuiContainer) || Minecraft.getMinecraft().currentScreen instanceof TIMCGui || Minecraft.getMinecraft().currentScreen != null && !(Minecraft.getMinecraft().currentScreen instanceof GuiChat)) {
            nignog = (arrayOfKeyBinding1 = keys).length;
            if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
                for(int i = 0; i < 3; i++){
                    Minecraft.getMinecraft().thePlayer.rotationYaw++;
                }
            }
            if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
                for(int i = 0; i < 3; i++){
                    Minecraft.getMinecraft().thePlayer.rotationYaw--;
                }
            }
            if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
                for(int i = 0; i < 3; i++){
                    Minecraft.getMinecraft().thePlayer.rotationPitch--;
                }
            }
            if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
                for(int i = 0; i < 3; i++){
                    Minecraft.getMinecraft().thePlayer.rotationPitch++;
                }
            }
            for (hereInMyGarage = 0; hereInMyGarage < nignog; hereInMyGarage++) {
                KeyBinding key = arrayOfKeyBinding1[hereInMyGarage];
                key.pressed = Keyboard.isKeyDown(key.getKeyCode());
            }
        } else if (Objects.isNull(Minecraft.getMinecraft().currentScreen)) {
            nignog = (arrayOfKeyBinding1 = keys).length;
            for (hereInMyGarage = 0; hereInMyGarage < nignog; hereInMyGarage++) {
                KeyBinding bind = arrayOfKeyBinding1[hereInMyGarage];
                if (!Keyboard.isKeyDown(bind.getKeyCode())) {
                    KeyBinding.setKeyBindState(bind.getKeyCode(), false);
                }
            }
        }
    }
}
