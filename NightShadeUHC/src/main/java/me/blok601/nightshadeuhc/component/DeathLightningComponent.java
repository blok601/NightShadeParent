package me.blok601.nightshadeuhc.component;

import me.blok601.nightshadeuhc.event.CustomDeathEvent;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;

/**
 * Created by Blok on 12/18/2018.
 */
public class DeathLightningComponent extends Component {

    public DeathLightningComponent() {
        super("Death Lightning", Material.FENCE, true);
    }

    @EventHandler
    public void onDeath(CustomDeathEvent e) {
        if (!isEnabled()) {
            return;
        }

        if (e.isCancelled()) return;

        World world = e.getLocation().getWorld();
        world.strikeLightningEffect(e.getLocation().add(0, 10, 0));

    }
}
