package me.blok601.nightshadeuhc.packet;

import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import me.blok601.nightshadeuhc.UHC;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Blok on 8/21/2018.
 */
public class OldEnchanting implements Listener {

    private boolean lapis, hideEnchant, oldEnchantCosts, randomizeEnchants;
    private PrepareItemEnchant event;
    private String version;
    private JavaPlugin plugin;

    public OldEnchanting(UHC plugin) {
        this.lapis = true;
        this.hideEnchant = true;
        this.oldEnchantCosts = true;
        this.randomizeEnchants = true; //These can all be changed
        this.plugin = plugin;
        try {
            version = UHC.get().getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        } catch (ArrayIndexOutOfBoundsException ex) {
            Core.get().getLogManager().log(Logger.LogType.SEVERE, "Couldn't load 1.7 enchanting! Blok fix your shit:");
            ex.printStackTrace();
        }
        if (!setupVersionInterface()) {
            Core.get().getLogManager().log(Logger.LogType.SEVERE, "Couldn't load 1.7 enchanting! Blok fix your shit...");
        }

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private boolean setupVersionInterface() {
        if (version == null) return false;
        try {
            Class<?> clazz = Class.forName("me.blok601.nightshadeuhc.packet.PrepareItemEnchant" + version.replace("v", "_")); //Change this to path to PrepareItemEnchant interface
            event = (PrepareItemEnchant) clazz.getConstructor(UHC.class).newInstance(plugin); //Pass in main class here
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            return false;
        }
        return true;
    }

    @EventHandler
    public void prepareItemEnchant(PrepareItemEnchantEvent e) { //Call the interface methods
        if(e.getEnchantBlock().getType() == Material.ENCHANTMENT_TABLE){
            if (randomizeEnchants) event.randomizeSeed(e);
            if (oldEnchantCosts) event.oldEnchantCosts(e);
            if (hideEnchant) event.hideEnchants(e);
        }

    }

    @EventHandler
    public void enchantItem(EnchantItemEvent e) {
        if (lapis || oldEnchantCosts) {
           plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> { //Use old enchant costs (works with lapis)
                if (oldEnchantCosts)
                    e.getEnchanter().setLevel(e.getEnchanter().getLevel() - (e.getExpLevelCost() - (64 - e.getInventory().getItem(1).getAmount())));
                if (lapis) e.getInventory().setItem(1, new ItemStack(Material.INK_SACK, 64, (short) 4));
            }, 1);
        }
    }

    @EventHandler
    public void lapisClickEvent(InventoryClickEvent e) {// Prevent them thiefs from stealing lapis
        if (!lapis) return;
        if (e.getClickedInventory() != null && e.getClickedInventory().getType().equals(InventoryType.ENCHANTING)) {
            if (e.getRawSlot() == 1) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void inventoryOpen(InventoryOpenEvent e) {// Give free lapis
        if (!lapis) return;
        if (e.getInventory() != null && e.getInventory().getType().equals(InventoryType.ENCHANTING)) {
            e.getInventory().setItem(1, new ItemStack(Material.INK_SACK, 64, (short) 4));
        }
    }

    @EventHandler
    public void inventoryClose(InventoryCloseEvent e) {// Remove lapis so it doesn't drop on the ground. Prevents duping
        if (!lapis) return;
        if (e.getInventory() != null && e.getInventory().getType().equals(InventoryType.ENCHANTING)) {
            e.getInventory().setItem(1, null);
        }
    }
}
