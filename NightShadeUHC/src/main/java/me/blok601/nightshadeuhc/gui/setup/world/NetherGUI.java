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
 * Created by Blok on 12/14/2018.
 */
public class NetherGUI {

    public NetherGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, ChatUtils.format("&5Nether World Setup"));

        ItemBuilder toggler = new ItemBuilder(Material.PAPER)
                .name("&c&lEnable/Disable Nether")
                .lore("&eCurrently: " + (GameManager.get().isNetherEnabled() ? "&aEnabled" : "&cDisabled"));

        ItemBuilder nether = new ItemBuilder(Material.NETHERRACK)
                .name("&eNether World Setup")
                .lore("&7(&6i&7) &6Configure the nether in this gui");

        ItemBuilder create = new ItemBuilder(new ItemStack(Material.WOOL, 1, DyeColor.GREEN.getWoolData()))
                .name("&a&lCreate the Nether World")
                .lore("&7(&6i&7) &6Click to create the nether world")
                .lore("&cWarning: Make sure that everything is configured correctly!");

        ItemBuilder back = new ItemBuilder(Material.ARROW).name("&cBack");

        ItemBuilder currentBorder = new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getWoolData()))
                .name("&6Current Border")
                .lore("&eCurrent Border: " + GameManager.get().getSetupNetherRadius());

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

        inventory.setItem(3, toggler.make());
        inventory.setItem(4, nether.make());
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
