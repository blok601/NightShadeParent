package me.blok601.nightshadeuhc.entity.object;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 12/2/2017.
 */
public class PlayerRespawnObject {

    private ItemStack[] armor;
    private ItemStack[] items;
    private Location location;

    public PlayerRespawnObject(ItemStack[] armor, ItemStack[] items, Location location) {
        this.armor = armor;
        this.items = items;
        this.location = location;
    }

    public PlayerRespawnObject() {

    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public void setArmor(ItemStack[] armor) {
        this.armor = armor;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void setItems(ItemStack[] items) {
        this.items = items;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
