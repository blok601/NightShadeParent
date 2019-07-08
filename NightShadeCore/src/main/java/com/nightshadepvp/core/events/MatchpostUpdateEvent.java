package com.nightshadepvp.core.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Blok on 7/8/2019.
 */
public class MatchpostUpdateEvent extends Event {

    private String newMatchPost;

    public MatchpostUpdateEvent(String newMatchPost) {
        this.newMatchPost = newMatchPost;
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
}
