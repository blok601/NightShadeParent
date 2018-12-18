package com.nightshadepvp.core.engine;

import com.massivecraft.massivecore.Engine;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.ubl.UBLHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

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

    @EventHandler
    public void preLogin(AsyncPlayerPreLoginEvent e) {
        UUID uuid = e.getUniqueId();
        UBLHandler handler = Core.get().getUblHandler();

        if (handler.isUBLed(uuid) && !handler.isExempt(e.getName())) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, handler.getBanMessage(uuid));
        }

    }
}
