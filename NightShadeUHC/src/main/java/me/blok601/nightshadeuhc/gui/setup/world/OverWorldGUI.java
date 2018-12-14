package me.blok601.nightshadeuhc.gui.setup.world;

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
 * Created by Blok on 12/13/2018.
 */
public class OverWorldGUI {

    public OverWorldGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, ChatUtils.format("&5Overworld Setup"));

        ItemBuilder grass = new ItemBuilder(Material.GRASS)
                .name("&eOverworld Creator")
                .lore("&7&o(&6i&7) &6Configure the border and seed in this gui");

        ItemBuilder seedClick = new ItemBuilder(Material.SIGN)
                .name("&eSet the Seed")
                .lore("&7&o(&6i&7) &6Click to set the seed for the map");

        ItemBuilder back = new ItemBuilder(Material.ARROW)
                .name("&cBack");

        ItemBuilder create = new ItemBuilder(new ItemStack(Material.WOOL, 1, DyeColor.GREEN.getWoolData()))
                .name("&a&lCreate the World")
                .lore("&7&o(&6i&7) &6Create the game world")
                .lore("&cWarning: Make sure everything is configured correctly!");

        ItemBuilder currentBorder = new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getWoolData()))
                .name("&6Current Border")
                .lore("&eCurrent Border: " + GameManager.get().getSetupRadius());

        ItemBuilder plus5 = new ItemBuilder(Material.EMERALD_BLOCK)
                .name("&a+5")
                .lore("&7&o(&6i&7) &aClick to increase the radius by 5");
        ItemBuilder plus10 = new ItemBuilder(Material.EMERALD_BLOCK)
                .name("&a+10")
                .lore("&7&o(&6i&7) &aClick to increase the radius by 10");
        ItemBuilder plus50 = new ItemBuilder(Material.EMERALD_BLOCK)
                .name("&a+50")
                .lore("&7&o(&6i&7) &aClick to increase the radius by 50");
        ItemBuilder plus100 = new ItemBuilder(Material.EMERALD_BLOCK)
                .name("&a+100")
                .lore("&7&o(&6i&7) &aClick to increase the radius by 100");

        ItemBuilder minus5 = new ItemBuilder(Material.REDSTONE_BLOCK)
                .name("&c-5")
                .lore("&7&o(&6i&7) &cClick to decrease the radius by 5");
        ItemBuilder minus10 = new ItemBuilder(Material.REDSTONE_BLOCK)
                .name("&c-10")
                .lore("&7&o(&6i&7) &cClick to decrease the radius by 100");
        ItemBuilder minus50 = new ItemBuilder(Material.REDSTONE_BLOCK)
                .name("&c-50")
                .lore("&7&o(&6i&7) &cClick to decrease the radius by 50");
        ItemBuilder minus100 = new ItemBuilder(Material.REDSTONE_BLOCK)
                .name("&c-100")
                .lore("&7&o(&6i&7) &cClick to decrease the radius by 100");

        inventory.setItem(3, seedClick.make());
        inventory.setItem(4, grass.make());
        inventory.setItem(5, create.make());
        inventory.setItem(8, back.make());
        inventory.setItem(18, minus100.make());
        inventory.setItem(19, minus50.make());
        inventory.setItem(20, minus10.make());
        inventory.setItem(21, minus5.make());
        inventory.setItem(22, currentBorder.make());
        inventory.setItem(23, plus5.make());
        inventory.setItem(24, plus10.make());
        inventory.setItem(25, plus50.make());
        inventory.setItem(26, plus100.make());

        player.openInventory(inventory);


    }
}
