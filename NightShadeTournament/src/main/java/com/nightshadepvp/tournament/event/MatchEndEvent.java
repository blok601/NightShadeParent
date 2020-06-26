package com.nightshadepvp.tournament.event;

import com.nightshadepvp.tournament.entity.objects.game.iMatch;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blok on 8/11/2018.
 */
public class MatchEndEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private iMatch match;

    public MatchEndEvent(iMatch match) {
        this.match = match;
    }

    public iMatch getMatch() {
        return match;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
