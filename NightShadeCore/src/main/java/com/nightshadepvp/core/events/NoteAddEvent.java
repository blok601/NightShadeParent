package com.nightshadepvp.core.events;

import com.nightshadepvp.core.entity.NSPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Date;

public class NoteAddEvent extends Event {

    private String noteContent;
    private Date timestamp;
    private NSPlayer issuer;
    private NSPlayer issued;

    public NoteAddEvent(String noteContent, Date timestamp, NSPlayer issuer, NSPlayer issued) {
        this.noteContent = noteContent;
        this.timestamp = timestamp;
        this.issuer = issuer;
        this.issued = issued;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public NSPlayer getIssuer() {
        return issuer;
    }

    public NSPlayer getIssued() {
        return issued;
    }

    private static final HandlerList handlerList = new HandlerList();

    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
