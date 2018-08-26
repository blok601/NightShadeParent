package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by Blok on 1/28/2018.
 */
public class BedBombScenario extends Scenario{

    public BedBombScenario(){
        super ("BedBomb", "When you place a bed, it explodes", new ItemBuilder(Material.BED).name("BedBomb").make());
    }

    @EventHandler
    public void onPLace(BlockPlaceEvent e){
        if(!isEnabled()){
            return;
        }

        if(e.getBlock().getType() == Material.BED || e.getBlock().getType() == Material.BED_BLOCK){
            e.getBlock().setType(Material.AIR);
            e.getBlock().getWorld().createExplosion(e.getBlock().getLocation().add(0.5, 0.5,0.5), 3, false);
        }

    }

}
