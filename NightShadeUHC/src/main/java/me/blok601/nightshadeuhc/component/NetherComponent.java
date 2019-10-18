package me.blok601.nightshadeuhc.component;

import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;

/**
 * Created by Blok on 12/2/2018.
 */
public class NetherComponent extends Component {

    public NetherComponent() {
        super("Nether", Material.NETHERRACK, false, "Toggle player's traveling to the nether");
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void on(EntityPortalEvent event) {

        if (isEnabled()) {
            return; //This is for blocking
        }

        if (event.getTo() == null) return;

        if (event.getTo().getWorld().getEnvironment() == World.Environment.NETHER) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void on(PlayerPortalEvent event) {
        if (isEnabled()) {
            return; //This is for blocking
        }
        if (event.getTo() == null) return;

        if (event.getTo().getWorld().getEnvironment() == World.Environment.NETHER) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatUtils.message("&cTravelling to the nether is disabled!"));
        }
    }

}
