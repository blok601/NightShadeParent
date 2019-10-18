package com.nightshadepvp.tournament.event;

import com.nightshadepvp.tournament.entity.TPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blok on 8/11/2018.
 */
public class PlayerUnSpectateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private TPlayer tPlayer;

    /**
     * The default constructor is defined for cleaner code. This constructor
     * assumes the event is synchronous.
     */
    public PlayerUnSpectateEvent(TPlayer tPlayer) {
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
