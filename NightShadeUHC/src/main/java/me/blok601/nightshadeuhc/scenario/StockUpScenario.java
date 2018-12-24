package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.event.CustomDeathEvent;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;

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

        for (UHCPlayer uhcPlayer : UHCPlayerColl.get().getAllPlaying()) {
            if (uhcPlayer.getPlayer() == null) continue;
            uhcPlayer.getPlayer().setMaxHealth(uhcPlayer.getPlayer().getMaxHealth() + 2);
        }

    }
}
