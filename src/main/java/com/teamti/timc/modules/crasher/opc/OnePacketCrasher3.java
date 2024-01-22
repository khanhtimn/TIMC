package com.teamti.timc.modules.crasher.opc;

import com.teamti.timc.event.EventManager;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import com.teamti.timc.util.crasher.CrasherUtil;
import org.lwjgl.input.Keyboard;

public class OnePacketCrasher3 extends Module {


    public static boolean isEnabled;
    private int time = 0;


    public OnePacketCrasher3() {
        super("OnePacket 2", Keyboard.KEY_NONE);
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
        if (isEnabled) {

            if (time != 300) {
                time = time + 1;
            } else {
                toggle();
                time = 0;
            }

            CrasherUtil.INSTANCE.onePacketCrasherBookSign();

        }

    }
}


