package com.nightshadepvp.tournament.entity.objects.data;

import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 6/12/2018.
 */
public class Kit {

    private String name;
    private ItemStack[] items;
    private ItemStack[] armor;
    private ItemStack display;
    private boolean build;
    private boolean allowRegen;


    public Kit() {
    }

    public Kit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void setItems(ItemStack[] items) {
        this.items = items;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public void setArmor(ItemStack[] armor) {
        this.armor = armor;
    }

    public ItemStack getDisplay() {
        return display;
    }

    public void setDisplay(ItemStack display) {
        this.display = display;
    }

    public boolean isBuild() {
        return build;
    }

    public void setBuild(boolean build) {
        this.build = build;
    }

    public boolean isAllowRegen() {
        return allowRegen;
    }

    public void setAllowRegen(boolean allowRegen) {
        this.allowRegen = allowRegen;
    }
}
