package me.blok601.nightshadeuhc.component;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StrCompentent extends Component {


    public StrCompentent () {
        super("Strength Nerf", Material.POTION, true, "Toggle the Strength Nerf");
    }
    @EventHandler
    public void strNerf(EntityDamageByEntityEvent e){

        if (!isEnabled()) return;
        if(e.getEntity() instanceof Player) {
            if(e.getDamager() instanceof Player){
                Player p = (Player) e.getDamager();

                for (PotionEffect effect : p.getActivePotionEffects()) {
                    if(effect.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
                        e.setDamage(e.getDamage() * 0.70 );
                    }
                }

            }
        }
    }
}
