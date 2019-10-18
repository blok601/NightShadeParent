package com.nightshadepvp.core.events;

import com.nightshadepvp.core.Rank;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

/**
 * Created by Blok on 11/10/2017.
 */
public class RankChangeEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    private UUID playerUUID;
    private Rank toRank;

    /**
     * The default constructor is defined for cleaner code. This constructor
     * assumes the event is synchronous.
     */
    public RankChangeEvent(UUID playerUUID, Rank toRank) {
        this.playerUUID = playerUUID;
        this.toRank = toRank;
    }

    /**
     * This constructor is used to explicitly declare an event as synchronous
     * or asynchronous.
     *
     * @param isAsync true indicates the event will fire asynchronously, false
     *                by default from default constructor
     */
    public RankChangeEvent(boolean isAsync, UUID playerUUID, Rank toRank) {
        super(isAsync);
        this.playerUUID = playerUUID;
        this.toRank = toRank;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public Rank getToRank() {
        return toRank;
    }
}
