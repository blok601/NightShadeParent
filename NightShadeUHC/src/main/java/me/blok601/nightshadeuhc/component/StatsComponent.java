package me.blok601.nightshadeuhc.component;

import me.blok601.nightshadeuhc.event.UHCStatUpdateEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;

public class StatsComponent extends Component{

    public StatsComponent() {
        super("Stats", Material.DIAMOND_BLOCK, true, "Toggle whether stats should be enabled or not");
    }

    @EventHandler
    public void onStat(UHCStatUpdateEvent event){
        if(!isEnabled()){
            event.setCancelled(true);
        }
    }
}
