package me.blok601.nightshadeuhc.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * Created by Blok on 6/20/2018.
 */
public class CustomDeathEvent extends Event implements Cancellable{

    private static final HandlerList handlers = new HandlerList();
    private Player killed;
    private Player killer;
    private ArrayList<ItemStack> items;
    private Location location;

    private boolean cancelled;
    private boolean dropItems;

    private boolean usedProjectile;
    private Projectile projectile;

    /**
     * The default constructor is defined for cleaner code. This constructor
     * assumes the event is synchronous.
     */
    public CustomDeathEvent(Player killed, Player killer, ArrayList<ItemStack> items, Location location, boolean dropItems) {
        this.killed = killed;
        this.killer = killer;
        this.items = items;
        this.location = location;
        this.dropItems = dropItems;
    }

    public Player getKilled() {
        return killed;
    }

    public void setKilled(Player killed) {
        this.killed = killed;
    }

    public Player getKiller() {
        return killer;
    }

    public void setKiller(Player killer) {
        this.killer = killer;
    }

    public ArrayList<ItemStack> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemStack> items) {
        this.items = items;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public boolean isDropItems() {
        return dropItems;
    }

    public void setDropItems(boolean dropItems) {
        this.dropItems = dropItems;
    }

    public boolean usedProjectile() {
        return usedProjectile;
    }

    public void setUsedProjectile(boolean usedProjectile) {
        this.usedProjectile = usedProjectile;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public void setProjectile(Projectile projectile) {
        this.projectile = projectile;
    }
}
