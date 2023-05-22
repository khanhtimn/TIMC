package com.teamti.timc.event.impl.game;

import com.teamti.timc.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cedo
 * @since 03/30/2022
 */
@AllArgsConstructor
@Getter
public class RenderTickEvent extends Event.StateEvent {
    private final float ticks;
}
