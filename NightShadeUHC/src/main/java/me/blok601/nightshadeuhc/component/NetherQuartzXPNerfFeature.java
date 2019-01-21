package me.blok601.nightshadeuhc.component;

import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.manager.GameManager;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Blok on 12/18/2018.
 */
public class NetherQuartzXPNerfFeature extends Component {


    private GameManager gameManager;

    public NetherQuartzXPNerfFeature(GameManager gameManager) {
        super("Nether Quartz XP Nerf", Material.QUARTZ_ORE, true, "Toggle the nether quartz XP nerf for the nether");

        this.gameManager = gameManager;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!isEnabled()) {
            return;
        }

        if (!GameState.gameHasStarted()) return;
        Block block = e.getBlock();

        if (block.getWorld().getEnvironment() == World.Environment.NETHER) {
            if (gameManager.getNetherWorld() != null) {
                if (block.getWorld().getName().equalsIgnoreCase(gameManager.getNetherWorld().getName())) {
                    //in the nther world
                    e.setExpToDrop(e.getExpToDrop() / 2);
                }
            }
        }else if(block.getWorld().getName().equalsIgnoreCase(gameManager.getWorld().getName())){
            e.setExpToDrop(e.getExpToDrop() / 3);
        }
    }
}
