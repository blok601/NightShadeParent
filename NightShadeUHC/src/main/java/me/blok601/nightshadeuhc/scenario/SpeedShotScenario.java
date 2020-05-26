package me.blok601.nightshadeuhc.scenario;

import com.nightshadepvp.core.utils.LocationUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpeedShotScenario extends Scenario{

    public SpeedShotScenario() {
        super("Speed Shot", "Hitting a longshot of 50 blocks or more gives you Speed II for 10 seconds", new ItemBuilder(Material.BOW).name("Speed Shot").make());
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        if(!isEnabled()) return;

        Entity entity = event.getDamager();
        Entity damaged = event.getEntity();
        if(!(entity instanceof Arrow) || !(damaged instanceof Player)) return;
        Arrow arrow = (Arrow) entity;
        if(!(arrow.getShooter() instanceof Player)) return;
        Player shooter = (Player) arrow.getShooter();

        Location shooterLocation = shooter.getLocation();
        Location shotLocation = damaged.getLocation();

        if(shooterLocation.distance(shotLocation) >= 50){
            shooter.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1, true, true));
            sendMessage(shooter, "&bYou now have &fSpeed II &bfor &f10 seconds");
        }
    }
}
