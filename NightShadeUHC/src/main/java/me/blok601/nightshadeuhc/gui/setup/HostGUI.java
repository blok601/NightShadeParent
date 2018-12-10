package me.blok601.nightshadeuhc.gui.setup;

import com.nightshadepvp.core.Core;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 12/9/2018.
 */
public class HostGUI {

    public HostGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatUtils.format("&5Game Setup"));

        ItemBuilder post = new ItemBuilder(new ItemStack(Material.WOOL, 1, DyeColor.ORANGE.getWoolData())).name("&6&lSet the Matchpost").lore("&7&o(&6i&7) &6Click to set the matchpost for the UHC");
        if (!Core.get().getMatchpost().equalsIgnoreCase("uhc.gg")) {//Its set
            post.lore("&7Current: &6" + Core.get().getMatchpost());
        }

        ItemBuilder world = new ItemBuilder(new ItemStack(Material.WOOL, 1, DyeColor.YELLOW.getWoolData()))
                .name("&e&lWorld Setup")
                .lore("&7&o(&6i&7) &6Click to do the world setup");
        if (GameManager.get().getWorld() != null) {
            world.lore("&7Current: &6" + GameManager.get().getWorld().getName());
        }

        ItemBuilder border = new ItemBuilder(new ItemStack(Material.WOOL, 1, DyeColor.LIGHT_BLUE.getWoolData()))
                .name("&b&lBorder Configuration")
                .lore("&7&o(&6i&7) &6Click to do the border setup");

        ItemBuilder toggleable = new ItemBuilder(new ItemStack(Material.WOOL, 1, DyeColor.CYAN.getWoolData()))
                .name("&3&lToggleable Options")
                .lore("&7&o(&6i&7) &6Click to view the toggleable options");

        ItemBuilder timers = new ItemBuilder(new ItemStack(Material.WOOL, 1, DyeColor.GREEN.getWoolData()))
                .name("&2&lTimers")
                .lore("&7&o(&6i&7) &6Click to setup the game timers");


        inventory.setItem(0, post.make());
        inventory.setItem(2, world.make());
        inventory.setItem(4, border.make());
        inventory.setItem(6, toggleable.make());
        inventory.setItem(8, timers.make());
        player.openInventory(inventory);
    }

}
