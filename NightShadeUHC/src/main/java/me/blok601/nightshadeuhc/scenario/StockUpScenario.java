package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.event.CustomDeathEvent;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.UUID;

/**
 * Created by Blok on 1/28/2018.
 */
public class StockUpScenario extends Scenario{

    public StockUpScenario(){
        super("StockUp", "Every time a player dies, everyone gets one added to their max health", new ItemBuilder(Material.FERMENTED_SPIDER_EYE).name("StockUp").make());
    }

    @EventHandler
    public void onDeath(CustomDeathEvent e) {

        if (!isEnabled()) return;

        for (UUID uuid : UHC.players) {
            Player p = Bukkit.getPlayer(uuid);
            if (p == null) continue;
            p.setMaxHealth(p.getMaxHealth() + 2);
        }

    }
}
