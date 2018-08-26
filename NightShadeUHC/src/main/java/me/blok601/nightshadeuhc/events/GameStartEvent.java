package me.blok601.nightshadeuhc.events;


import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blok on 6/29/2017.
 */
public class GameStartEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public GameStartEvent(){

    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
