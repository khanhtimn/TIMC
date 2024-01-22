package com.teamti.timc.modules;

import com.teamti.timc.event.EventManager;
import com.teamti.timc.event.EventTarget;
import com.teamti.timc.event.events.EventTick;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import com.teamti.timc.util.BooleanValue;
import com.teamti.timc.util.NumberValue;
import com.teamti.timc.util.RandomUtil;
import com.teamti.timc.util.TimerUtil;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class AutoClicker extends Module {

    public static boolean isEnabled;
    public BooleanValue swordsOnly;
    public NumberValue minCPS;
    public NumberValue maxCPS;
    private TimerUtil timer = new TimerUtil();

    public AutoClicker() {
        super("AutoClicker", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(0);
        swordsOnly = new BooleanValue("Swords and Axes Only", true);
        addValue(swordsOnly);
        minCPS = new NumberValue("MinCPS", 7, 1, 20, 1);
        addValue(minCPS);
        maxCPS = new NumberValue("MaxCPS", 11, 1, 20, 1);
        addValue(maxCPS);
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
    public void onEvent(EventTick event) {
        setDisplayName("AutoClicker");
        setDisplayName(getDisplayName() + " [" + minCPS.getValue() + "-" + maxCPS.getValue() + "]");

            int cps = RandomUtil.RandomShit.INSTANCE.getRandomInt((int)minCPS.getValue(), (int)maxCPS.getValue());
        if (timer.hasTimeElapsed((1000 / cps),false) && mc.currentScreen == null && Mouse.isButtonDown(0) && (!swordsOnly.getValue() || isHoldingSwordOrAxe())) {
            if (mc.objectMouseOver != null) {
                switch (mc.objectMouseOver.typeOfHit) {
                    case ENTITY:
                        mc.thePlayer.stopUsingItem();
                        mc.thePlayer.swingItem();
                        mc.getNetHandler().getNetworkManager().sendPacket(new C02PacketUseEntity(mc.objectMouseOver.entityHit, C02PacketUseEntity.Action.ATTACK));
                        break;
                    case BLOCK:
                        break;
                    case MISS:
                        mc.thePlayer.swingItem();
                        break;
                }
            }
            timer.reset();
        }
    }

    private boolean isHoldingSwordOrAxe() {
        return mc.thePlayer.getCurrentEquippedItem() != null && (mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword || mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemAxe);
    }
}