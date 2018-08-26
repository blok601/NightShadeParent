package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Blok on 6/24/2018.
 */
public class DepthsScenario extends Scenario{


    public DepthsScenario() {
        super("Depths", "Mobs do more damage the deeper into the cave you are", new ItemBuilder(Material.REDSTONE_TORCH_ON).name("Depths").make());
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){

        if(!isEnabled()) return;

        if(!(e.getEntity() instanceof Player)){
            return;
        }

        if(e.getDamager() instanceof Player){
            return;
        }

        if(e.isCancelled()) return;
        if (e.getDamager() instanceof Monster) {

            Player p = (Player) e.getEntity();
            double damage = e.getDamage();
            double y = p.getLocation().getY();
            if (y < 15) {
                e.setDamage(damage * 4);
            } else if (y < 30) {
                e.setDamage(damage * 3);
            } else if (y < 45) {
                e.setDamage(damage * 2);
            } else if (y < 60) {
                e.setDamage(damage * 1.5);
            }
        }
        if (e.getDamager() instanceof Projectile){
            Projectile projectile = (Projectile) e.getDamager();
            if (projectile.getShooter() instanceof Skeleton) {
                Player p = (Player) e.getEntity();
                double damage = e.getDamage();
                double y = p.getLocation().getY();
                if (y < 15) {
                    e.setDamage(damage * 4);
                } else if (y < 30) {
                    e.setDamage(damage * 3);
                } else if (y < 45) {
                    e.setDamage(damage * 2);
                } else if (y < 60) {
                    e.setDamage(damage * 1.5);
                }
            }
        }
    }
}
