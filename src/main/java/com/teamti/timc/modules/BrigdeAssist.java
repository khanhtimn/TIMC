package com.teamti.timc.modules;

import com.teamti.timc.event.EventManager;
import com.teamti.timc.event.EventTarget;
import com.teamti.timc.event.events.EventMotion;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import com.teamti.timc.util.BooleanValue;
import com.teamti.timc.util.NumberValue;
import com.teamti.timc.util.TimerUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

public class BrigdeAssist extends Module {

    public static boolean isEnabled;

    public BooleanValue autoPlace;
    public NumberValue delay;
    TimerUtil TimerUtil = new TimerUtil();

    public BrigdeAssist() {
        super("BrigdeAssist", Keyboard.KEY_H);
        this.jelloCat = TIMC.tabgui.cats.get(0);
        delay = new NumberValue("Delay", 150, 150, 500, 0.1);
        addValue(delay);
        autoPlace = new BooleanValue("Auto Place", false);
        addValue(autoPlace);
    }


    @EventTarget
    public void onMotion(EventMotion e)
    {
        try
        {
            if (mc.currentScreen == null && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ)).getBlock() == Blocks.air /*&& mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock*/)
            {
                if (autoPlace.getValue() && this.TimerUtil.delay((float) delay.getValue()))
                {
                    mc.gameSettings.keyBindSneak.pressed = true;
                    this.mc.rightClickMouse();
                    //azoq.getClicker().rightClickMouse();
                    this.TimerUtil.reset();
                }
                else
                {
                    mc.gameSettings.keyBindSneak.pressed = true;
                }
            }
            else
            {
                mc.gameSettings.keyBindSneak.pressed = false;
            }
        }
        catch (Exception var3)
        {
            mc.gameSettings.keyBindSneak.pressed = false;
        }
    }

    public void onEnable(){
        isEnabled = true;
        EventManager.register(this);
    }

    public void onDisable()
    {
        EventManager.unregister(this);
        mc.gameSettings.keyBindSneak.pressed = false;
        isEnabled = false;
    }
}
