package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.util.ItemBuilder;
import me.blok601.nightshadeuhc.util.MathUtils;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class InfestationScenario extends Scenario{

    public InfestationScenario() {
        super("Infestation", "When you kill a mob there's a 40% chance it respawns", new ItemBuilder(Material.FEATHER).name("Infestation").make());
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        if(!isEnabled()) return;
        Entity killed = event.getEntity();
        if(killed instanceof Player) return;
        if(killed instanceof Animals || killed instanceof Monster){
            if(MathUtils.getChance(40)){
                killed.getWorld().spawnEntity(killed.getLocation(), killed.getType());
            }
        }
    }
}
