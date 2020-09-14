package com.nightshadepvp.nightcheat.listener;

import com.nightshadepvp.nightcheat.NightCheat;
import com.nightshadepvp.nightcheat.cheat.combat.FightData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

public class PlayerListener implements Listener {

    private NightCheat plugin;

    public PlayerListener(NightCheat plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        Entity damager = event.getDamager();
        if(!(damager instanceof Player)) return;
        Entity damaged = event.getEntity();
        if(!(damaged instanceof Player)) return;

        Player damagerP = (Player) damager;
        FightData fightData = FightData.getFightData(damagerP);
        fightData.setLastDamage(System.currentTimeMillis());
        new BukkitRunnable(){
            @Override
            public void run() {
                Duration then = Duration.ofSeconds(fightData.getLastDamage());
                Duration now = Duration.ofSeconds(System.currentTimeMillis());
                if(now.minus(then).getSeconds() >= 20){
                    fightData.setFighting(false);
                    fightData.setSwungArm(false);
                }
            }
        }.runTaskLaterAsynchronously(plugin, 400);
    }
}
