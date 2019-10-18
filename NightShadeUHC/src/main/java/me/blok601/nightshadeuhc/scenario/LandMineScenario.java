package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.object.PlayerStatus;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class LandMineScenario extends Scenario{
    public LandMineScenario() {
        super("Landmine", "Stone Pressure Plates go BOOM!", new ItemBuilder(Material.STONE_PLATE).name("Landmine").make());
    }

    @EventHandler
    public void onStep (PlayerInteractEvent e) {
        if(!isEnabled()) return;
        if(e.getAction().equals(Action.PHYSICAL)) {
            if (e.getClickedBlock().getType() == Material.STONE_PLATE) {
                Player p = e.getPlayer();
                if (UHCPlayer.get(p).getPlayerStatus() == PlayerStatus.PLAYING) {
                    final Location l = p.getLocation().clone();

                    l.getWorld().createExplosion(l.add(0.5, 0.5, 0.5), 3, false);
                }

            }
        }
    }

}
