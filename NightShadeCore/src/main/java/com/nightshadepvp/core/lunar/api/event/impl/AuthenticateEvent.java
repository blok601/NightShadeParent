package com.nightshadepvp.core.lunar.api.event.impl;

import com.nightshadepvp.core.lunar.api.event.PlayerEvent;
import org.bukkit.entity.Player;

public class AuthenticateEvent extends PlayerEvent {

    public AuthenticateEvent(Player player) {
        super(player);
    }

}
