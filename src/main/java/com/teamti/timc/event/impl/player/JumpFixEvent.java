package com.teamti.timc.event.impl.player;

import com.teamti.timc.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JumpFixEvent extends Event {
    private float yaw;
}
