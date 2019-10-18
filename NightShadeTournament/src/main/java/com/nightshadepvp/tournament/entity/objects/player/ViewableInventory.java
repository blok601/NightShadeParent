package com.nightshadepvp.tournament.entity.objects.player;

import com.nightshadepvp.core.utils.ItemBuilder;
import com.nightshadepvp.tournament.utils.ChatUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

/**
 * Created by Blok on 8/10/2017.
 */
public class ViewableInventory {

    @Getter
    private UUID id;
    @Getter
    private String name;
    @Getter
    private double health;
    @Getter
    private double food;
    private List<String> effects;
    private PlayerInv inv;

    public ViewableInventory(String name, double health, double food, List<String> effects, PlayerInv inv) {
        this.name = name;
        this.health = health;
        this.food = food;
        this.effects = effects;
        this.inv = inv;
        this.id = UUID.randomUUID();
    }

    public Inventory getInventory(){
        Inventory inve = Bukkit.createInventory(null, 54,this.name + "'s Inventory" );

        for (int i = 9; i <= 35; ++i) {
            inve.setItem(i - 9, this.inv.getContents()[i]);
        }

        for (int i = 0; i <= 8; ++i) {
            inve.setItem(i + 27, this.inv.getContents()[i]);
        }

        inve.setItem(36, this.inv.getHelmet());
        inve.setItem(37, this.inv.getChestPiece());
        inve.setItem(38, this.inv.getLeggings());
        inve.setItem(39, this.inv.getBoots());

        if(this.health == 0.0){
            inve.setItem(48, new ItemBuilder(Material.SKULL_ITEM).name( ChatColor.RED + "Player Died").make());
        }else{
            inve.setItem(48, new ItemBuilder(Material.SKULL_ITEM).name(ChatColor.BLUE + "Player Health").lore(ChatUtils.format("&c" + (this.health / 2.0) + " &cHearts")).make());
        }

        inve.setItem(49, new ItemBuilder(Material.COOKED_BEEF).name(ChatUtils.format("&9Player Hunger")).lore(ChatUtils.format("&c" + (this.food / 2.0) + " &cHunger")).make());
        ItemStack stack = new ItemBuilder(Material.POTION).name(ChatUtils.format("&9Potion Effects")).make();
        ItemMeta meta = stack.getItemMeta();
        meta.setLore(effects);
        stack.setItemMeta(meta);
        inve.setItem(50, stack);

        return inve;
    }
}
