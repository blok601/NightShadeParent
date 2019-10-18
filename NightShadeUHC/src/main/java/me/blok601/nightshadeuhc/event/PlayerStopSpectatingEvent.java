package me.blok601.nightshadeuhc.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


/**
 * Created by Blok on 1/21/2019.
 */
public class PlayerStopSpectatingEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;

    /**
     * The default constructor is defined for cleaner code. This constructor
     * assumes the event is synchronous.
     */
    public PlayerStopSpectatingEvent(Player player) {
        this.player = player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }
}