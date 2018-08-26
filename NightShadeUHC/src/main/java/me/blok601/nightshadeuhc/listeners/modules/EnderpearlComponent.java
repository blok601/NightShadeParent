package me.blok601.nightshadeuhc.listeners.modules;

import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Blok on 12/10/2017.
 */
public class EnderpearlComponent extends Component{


    public EnderpearlComponent() {
        super("Enderpearl Damage", new ItemBuilder(Material.ENDER_PEARL).name(ChatUtils.format("&eEnderpearl Damage")).make() ,true);
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
