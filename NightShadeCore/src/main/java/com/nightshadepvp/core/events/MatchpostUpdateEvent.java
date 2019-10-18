package com.nightshadepvp.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blok on 7/8/2019.
 */
public class MatchpostUpdateEvent extends Event {

    private String newMatchPost;
    private Player updater;

    public MatchpostUpdateEvent(String newMatchPost, Player updater) {
        this.newMatchPost = newMatchPost;
        this.updater = updater;
    }

    public String getNewMatchPost() {
        return newMatchPost;
    }

    private static final HandlerList handlerList = new HandlerList();

    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Player getUpdater() {
        return updater;
    }
}
