package com.nightshadepvp.core.entity.objects;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Blok on 12/10/2017.
 */
public abstract class Quest implements Listener {

    private String name;
    private Material material;
    private String description;
    int id;

    public Quest(String name, String description, Material material, int id) {
        this.name = name;
        this.material = material;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Material getMaterial() {
        return material;
    }


    public void onComplete(){}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
