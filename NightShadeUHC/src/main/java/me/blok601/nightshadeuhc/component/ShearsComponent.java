package me.blok601.nightshadeuhc.component;

import com.google.common.collect.ImmutableList;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.util.MathUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 12/22/2018.
 */
public class ShearsComponent extends Component {

    private GameManager gameManager;

    protected ImmutableList list;

    public ShearsComponent(GameManager gameManager) {
        super("Shears", Material.SHEARS, false, "Toggle whether shears work for apples or not");
        this.gameManager = gameManager;

        list = ImmutableList.of(Material.LEAVES, Material.LEAVES_2);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!isEnabled()) return;

        Player p = e.getPlayer();
        if (p.getItemInHand().getType() != Material.SHEARS) return;

        Block block = e.getBlock();
        if (!list.contains(block.getType())) {
            return;
        }

        //Shears and leaves
        if (MathUtils.getChance(gameManager.getAppleRates())) {
                e.getBlock().setType(Material.AIR);
                e.getBlock().getDrops().clear();
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.APPLE));
        }

    }
}
