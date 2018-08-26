package com.nightshadepvp.core.entity.objects;

import com.nightshadepvp.core.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 12/15/2017.
 */
public enum PlayerColor {

    DEFAULT(ChatColor.GRAY, new ItemBuilder(Material.PAPER).name("&7Default").make()),
    WHITE(ChatColor.WHITE, new ItemBuilder(Material.PAPER).name("&fWhite").make()),
    BLUE(ChatColor.DARK_AQUA, new ItemBuilder(Material.PAPER).name("&3Blue").make()),
    RED(ChatColor.RED, new ItemBuilder(Material.PAPER).name("&cRed").make()),
    GOLD(ChatColor.GOLD, new ItemBuilder(Material.PAPER).name("&6Gold").make()),
    GREEN(ChatColor.GREEN, new ItemBuilder(Material.PAPER).name("&2Green").make());

    private ChatColor color;
    private ItemStack stack;

    PlayerColor(ChatColor color, ItemStack stack) {
        this.color = color;
        this.stack = stack;
    }

    public ChatColor getColor() {
        return color;
    }

    public ItemStack getStack() {
        return stack;
    }

    public static PlayerColor getColor(ItemStack item){
        for (PlayerColor r : PlayerColor.values()) {
            if (r.getStack().equals(item)) return r;
        }

        return  null;
    }
}
