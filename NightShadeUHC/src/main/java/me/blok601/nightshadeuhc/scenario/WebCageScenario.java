package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.util.ItemBuilder;
import me.blok601.nightshadeuhc.util.Util;
import me.blok601.nightshadeuhc.event.CustomDeathEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.ArrayList;

/**
 * Created by Blok on 3/30/2018.
 */
public class WebCageScenario extends Scenario{


    public WebCageScenario() {
        super("WebCage", "When a player dies their corpse is surrounded by cobwebs", new ItemBuilder(Material.WEB).name("WebCage").make());
    }


    @EventHandler
    public void onDeath(CustomDeathEvent e){
        if(!isEnabled()){
            return;
        }

        if(e.getLocation().getWorld().getName().toLowerCase().contains("spawn")){
            return;
        }

        Player p = e.getKilled();
        Location location = p.getLocation();


        ArrayList<Block> blocks = Util.getBlocks(location.getBlock(), 10);
        blocks.stream().filter(block -> Math.floor(block.getLocation().distance(location)) == 4).filter(block -> block.getType() == Material.AIR).forEach(block -> block.setType(Material.WEB));
    }
}
