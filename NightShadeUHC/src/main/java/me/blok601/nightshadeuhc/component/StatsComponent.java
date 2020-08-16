package me.blok601.nightshadeuhc.component;

import me.blok601.nightshadeuhc.event.PostUHCEvent;
import me.blok601.nightshadeuhc.event.PostWinnersEvent;
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

    @EventHandler
    public void onPost(PostUHCEvent event){
        if(!isEnabled()){
            event.setCancelled(true);
            event.setCancelReason("Games with stats disabled can't be posted to twitter.");
        }
    }

    @EventHandler
    public void onPost(PostWinnersEvent event){
        if(!isEnabled()){
            event.setCancelled(true);
            event.setCancelReason("Games with stats disabled can't be posted to twitter.");
        }
    }
}
