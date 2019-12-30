package me.blok601.nightshadeuhc.component;

import com.google.common.collect.Sets;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.object.GameState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;

public class BlockDecayComponent extends Component {

    private UHC uhc;

    public BlockDecayComponent(UHC plugin) {
        super("Block Decay", Material.MOSSY_COBBLESTONE, false, "Should blocks decay after 15 seconds in meetup");
        this.uhc = plugin;
    }


    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (!isEnabled()) return;

        if (GameState.getState() != GameState.MEETUP) return;
        Block block = e.getBlock();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (block != null && block.getType() != Material.AIR) {
                    block.setType(Material.AIR);
                }
            }
        }.runTaskLater(uhc, 200);
    }
}

