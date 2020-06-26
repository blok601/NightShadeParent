package com.nightshadepvp.tournament.event;

import com.nightshadepvp.tournament.entity.objects.game.iMatch;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blok on 8/11/2018.
 */
public class TournamentEndEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private iMatch championshipMatch;

    public TournamentEndEvent(iMatch championshipMatch) {
        this.championshipMatch = championshipMatch;
    }


    public iMatch getChampionshipMatch() {
        return championshipMatch;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
