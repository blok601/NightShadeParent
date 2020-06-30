package me.blok601.nightshadeuhc.event;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UHCStatUpdateEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList(){
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    private UHCPlayer uhcPlayer;

    public UHCStatUpdateEvent(UHCPlayer uhcPlayer) {
        this.uhcPlayer = uhcPlayer;
    }
}
