package me.blok601.nightshadeuhc.event;


import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blok on 3/7/2018.
 */
public class PlayerJoinGameLateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;

    /**
     * The default constructor is defined for cleaner code. This constructor
     * assumes the event is synchronous.
     */
    public PlayerJoinGameLateEvent(Player player) {
        this.player = player;
    }


    public Player getPlayer() {
        return player;
    }


    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
