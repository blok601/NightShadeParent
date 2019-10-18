package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by Blok on 2/11/2018.
 */
public class NoFallScenario extends Scenario {

    public NoFallScenario() {
        super("NoFall", "Players take no fall damage", new ItemBuilder(Material.LEATHER_BOOTS).name("NoFall").make());
    }

    @EventHandler
    public void on(EntityDamageEvent e){

        if(!isEnabled()){
            return;
        }

        if(e.getEntity() instanceof Player){
            if(e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                e.setCancelled(true);
            }
        }
    }
}
