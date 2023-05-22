package com.teamti.timc.event.impl.game;

import com.teamti.timc.event.Event;


public class KeyPressEvent extends Event {

    private final int key;

    public KeyPressEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

}
