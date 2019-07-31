package com.nightshadepvp.tournament.event;

import com.nightshadepvp.tournament.entity.TPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blok on 8/11/2018.
 */
public class PlayerSpectateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private TPlayer tPlayer;


    public PlayerSpectateEvent(TPlayer tPlayer) {
        this.tPlayer = tPlayer;
    }

    public TPlayer getTPlayer() {
        return tPlayer;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
