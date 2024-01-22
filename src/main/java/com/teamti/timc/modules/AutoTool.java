package com.teamti.timc.modules;

import com.teamti.timc.event.EventManager;
import com.teamti.timc.event.EventTarget;
import com.teamti.timc.event.events.EventMotion;
import com.teamti.timc.event.types.EventType2;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;


public class AutoTool extends Module {

    public static boolean isEnabled;

    public AutoTool() {
        super("AutoTool", Keyboard.KEY_R);
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
    public void onEvent(EventMotion e) {
        if (e.getType() == EventType2.PRE && mc.currentScreen == null && Mouse.isButtonDown(0) && mc.objectMouseOver != null) {
            BlockPos blockPos = mc.objectMouseOver.func_178782_a();
            if (blockPos != null) {
                Block block = mc.theWorld.getBlockState(blockPos).getBlock();
                float strength = 1.0F;
                int bestToolSlot = -1;

                for (int i = 0; i < 9; ++i) {
                    ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
                    if (itemStack != null && itemStack.getStrVsBlock(block) > strength) {
                        strength = itemStack.getStrVsBlock(block);
                        bestToolSlot = i;
                    }
                }

                if (bestToolSlot != -1) {
                    mc.thePlayer.inventory.currentItem = bestToolSlot;
                }
            }
        }

    }
//event.getType() == EventType2.PRE
}

