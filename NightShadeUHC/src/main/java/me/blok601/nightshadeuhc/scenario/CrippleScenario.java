package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CrippleScenario extends Scenario{

    public CrippleScenario() {
        super("Cripple", "When you take fall damage you get slowness for 30 seconds", new ItemBuilder(Material.CHAINMAIL_BOOTS).name("Cripple").make());
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){

        if(!isEnabled()) return;

        if(e.getCause() != EntityDamageEvent.DamageCause.FALL) return;

        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 0));
        }
    }
}
