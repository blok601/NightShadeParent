package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by Blok on 1/24/2018.
 */
public class FirelessScenario extends Scenario {

    public FirelessScenario() {
        super("Fireless", "Players can't take fire damage", new ItemBuilder(Material.FLINT_AND_STEEL).name("Fireless").make());
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {

        if (!isEnabled()) return;

        if (e.getEntity() instanceof Player) {
            if (e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || e.getCause() == EntityDamageEvent.DamageCause.LAVA) {
                e.setCancelled(true);
                e.setDamage(0);
                e.getEntity().setFireTicks(0);
            }
        }
    }
}
