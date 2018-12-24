package me.blok601.nightshadeuhc.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;
import java.util.UUID;

/**
 * Created by Blok on 8/18/2018.
 */
public class GameEndEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private List<UUID> winners;


    public GameEndEvent(List<UUID> winners) {
        this.winners = winners;
    }

    public List<UUID> getWinners() {
        return winners;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
