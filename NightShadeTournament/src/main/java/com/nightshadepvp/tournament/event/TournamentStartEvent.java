package com.nightshadepvp.tournament.event;

import com.nightshadepvp.tournament.entity.TPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blok on 8/11/2018.
 */
public class TournamentStartEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public TournamentStartEvent() {
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
