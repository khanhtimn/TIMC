package com.teamti.event.impl.render;


import com.teamti.event.Event;

public class ShaderEvent extends Event {

    public final boolean bloom;
    public ShaderEvent(boolean bloom){
        this.bloom = bloom;
    }
}
