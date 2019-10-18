package com.nightshadepvp.tournament.event;

import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.objects.game.iMatch;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blok on 8/11/2018.
 */
public class PlayerUnSpectateMatchEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private TPlayer tPlayer;
    private iMatch match;

    /**
     * The default constructor is defined for cleaner code. This constructor
     * assumes the event is synchronous.
     */
    public PlayerUnSpectateMatchEvent(TPlayer tPlayer, iMatch match) {
        this.tPlayer = tPlayer;
        this.match = match;
    }

    public TPlayer getTPlayer() {
        return tPlayer;
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
