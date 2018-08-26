package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.GameState;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.UUID;

/**
 * Created by Blok on 1/28/2018.
 */
public class StockUpScenario extends Scenario{

    public StockUpScenario(){
        super("StockUp", "Every time a player dies, they get one added to their max health", new ItemBuilder(Material.FERMENTED_SPIDER_EYE).name("StockUp").make());
    }

    private int healthIncreased = 0;

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        if (!isEnabled()) return;
        if (GameState.getState() != GameState.INGAME && GameState.getState() != GameState.MEETUP) return;

        healthIncreased = healthIncreased + 1;

        for (UUID uuid : UHC.players) {
            Player pl = Bukkit.getPlayer(uuid);
            if (pl == null) continue;
            pl.setMaxHealth(pl.getMaxHealth() + healthIncreased);
        }

    }
}
