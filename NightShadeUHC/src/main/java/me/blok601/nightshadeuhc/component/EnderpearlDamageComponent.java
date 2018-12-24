package me.blok601.nightshadeuhc.component;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Blok on 12/10/2017.
 */
public class EnderpearlDamageComponent extends Component{


    public EnderpearlDamageComponent() {
        super("Enderpearl Damage", Material.ENDER_PEARL, true);
    }


    @EventHandler(ignoreCancelled = true)
    public void on(EntityDamageByEntityEvent event) {
        if(isEnabled()){
            return;
        }
        
        if (!(event.getEntity() instanceof Player))
            return;

        if (event.getDamager().getType() != EntityType.ENDER_PEARL)
            return;

        event.setDamage(0.0);
        event.setCancelled(true);
    }
}
