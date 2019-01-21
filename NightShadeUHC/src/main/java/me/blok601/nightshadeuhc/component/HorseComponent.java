package me.blok601.nightshadeuhc.component;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.spigotmc.event.entity.EntityMountEvent;

/**
 * Created by Blok on 12/18/2018.
 */
public class HorseComponent extends Component {

    public HorseComponent() {
        super("Horses", Material.SADDLE, true, "Toggle whether horses can be ridden or not");
    }

    @EventHandler
    public void onMount(EntityMountEvent e) {
        if (isEnabled()) return;

        Entity entity = e.getEntity();
        if (!(entity instanceof Player)) {
            return;
        }

        Entity mount = e.getMount();
        if (mount.getType() != EntityType.HORSE) {
            return;
        }

        e.setCancelled(true);
        entity.sendMessage(ChatUtils.message("&cHorse riding is disabled!"));
    }

    @Override
    public void onToggle(boolean newState, Player p) {
        if (newState) return;

        Player player;
        for (UHCPlayer uhcPlayer : UHCPlayerColl.get().getAllPlaying()) {
            player = uhcPlayer.getPlayer();
            Entity vehicle = player.getVehicle();
            if(vehicle == null){
                continue;
            }

            if(vehicle.getType() != EntityType.HORSE){
                continue;
            }

            vehicle.eject();
            player.sendMessage(ChatUtils.message("&cHorses have been disabled by the host!"));

        }

    }
}
