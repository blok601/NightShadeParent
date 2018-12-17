package me.blok601.nightshadeuhc.component;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Blok on 12/13/2017.
 */
public class AbsorptionComponent extends Component{


    public AbsorptionComponent() {
        super("Absorption", new ItemBuilder(Material.GOLDEN_APPLE).name("&eAbsorption").make(), true);
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent e){
        if(isEnabled()){
           return;
        }

        if(e.getItem().getType() == Material.GOLDEN_APPLE){
            new BukkitRunnable(){
                @Override
                public void run() {
                    e.getPlayer().removePotionEffect(PotionEffectType.ABSORPTION);
                }
            }.runTaskLater(UHC.get(), 1);
        }
    }
}
