package me.blok601.nightshadeuhc.gui.setup.world;

import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by Blok on 12/9/2018.
 */
public class WorldGUI {

    public WorldGUI(Player player) {

        Inventory inventory = Bukkit.createInventory(null, 27, ChatUtils.format("&5World Setup"));

        ItemBuilder normal = new ItemBuilder(Material.GRASS)
                .name("&6Overworld Creation")
                .lore("&7&o(&6&oi&7&o) &6&oClick to start the game world creation process");

        ItemBuilder nether = new ItemBuilder(Material.NETHERRACK)
                .name("&6Nether Creation")
                .lore("&7&o(&6&oi&7&o) &6&oClick to start the nether world creation process");

        ItemBuilder clearWorlds = new ItemBuilder(Material.LAVA_BUCKET)
                .name("&6Clear your worlds")
                .lore("&7&o(&6&oi&7&o) &6&oClick to clear all of your game worlds");

        ItemBuilder back = new ItemBuilder(Material.ARROW)
                .name("&cBack");

        inventory.setItem(12, normal.make());
        inventory.setItem(14, nether.make());
        inventory.setItem(26, back.make());
        inventory.setItem(18, clearWorlds.make());

        player.openInventory(inventory);
    }
}
