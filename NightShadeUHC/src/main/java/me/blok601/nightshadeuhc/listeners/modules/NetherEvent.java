package me.blok601.nightshadeuhc.listeners.modules;

import me.blok601.nightshadeuhc.UHC;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;

public class NetherEvent implements Listener{
	
	FileConfiguration config = UHC.get().getConfig();
	
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void on(EntityPortalEvent event) {
        if (config.getBoolean("nether")) return;
        if (event.getTo() == null) return;

        if (event.getTo().getWorld().getEnvironment() == World.Environment.NETHER) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void on(PlayerPortalEvent event) {
        if (config.getBoolean("nether")) return;
        if (event.getTo() == null) return;

        if (event.getTo().getWorld().getEnvironment() == World.Environment.NETHER) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.DARK_AQUA + "[UHC] " + ChatColor.RED + "Travelling to the nether is disabled!");
        }
    }
}
