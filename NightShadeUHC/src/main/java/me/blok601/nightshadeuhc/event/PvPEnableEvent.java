package me.blok601.nightshadeuhc.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blok on 2/16/2018.
 */
public class PvPEnableEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public PvPEnableEvent(){

    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
