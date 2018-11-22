package com.nightshadepvp.core.engine;

import com.massivecraft.massivecore.Engine;
import com.nightshadepvp.core.entity.NSPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class EnginePunish extends Engine {

    private static EnginePunish i = new EnginePunish();
    public static EnginePunish get() { return i; }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        Player p = e.getPlayer();

        if (e.getFrom().getX() == e.getTo().getX() && e.getFrom().getZ() == e.getTo().getZ()) return;

        NSPlayer user = NSPlayer.get(p);

        if (!user.isFrozen()) return;

        e.setTo(e.getFrom());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        NSPlayer user = NSPlayer.get(e.getPlayer());

        if (!user.isFrozen()) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {

        NSPlayer user = NSPlayer.get(e.getPlayer());
        if (!user.isFrozen()) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            NSPlayer user = NSPlayer.get(p);
            if (user.isFrozen()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            NSPlayer user = NSPlayer.get(p);
            if (user.isFrozen()) e.setCancelled(true);
        }

        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            NSPlayer user = NSPlayer.get(p);
            if (user.isFrozen()) e.setCancelled(true);
        }
    }
}
