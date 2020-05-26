package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Blok on 12/3/2017.
 */
public class TimberScenario extends Scenario{

    public TimberScenario(){
        super("Timber", "When you break one part of the tree the whole tree falls", new ItemBuilder(Material.LOG).name("Timber").make());
    }


    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        if (!isEnabled()) return;

        if (event.isCancelled()) return;
        if (GameState.getState() != GameState.INGAME && GameState.getState() != GameState.MEETUP) return;

        handleBreak(event.getBlock(), 0);

    }

    private void handleBreak(Block tree, int broken) {

        if (broken > 20) return; // Prevent an infinite For Loop

        if (tree == null || tree.getType() == Material.AIR) return; // We've seen a block thats not a log. so stop.

        if (tree.getType() != Material.LOG && tree.getType() != Material.LOG_2)
            return; // Ensure the block were breaking is a log type

        tree.breakNaturally(); // Break it

        // Loop for each block face to find what we are breaking next and call the method
        for (BlockFace face : BlockFace.values()) {
            handleBreak(tree.getRelative(face), (broken + 1));
        }

    }

}
