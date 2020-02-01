package me.blok601.nightshadeuhc.event;


import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

//        Bukkit.getServer().getPluginManager().callEvent(new PlayerStopSpectatingEvent(p));
public class FinalHealEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public FinalHealEvent(){

    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}

