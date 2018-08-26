package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Blok on 6/24/2018.
 */
public class BlockedScenario extends Scenario{

    HashMap<UUID, ArrayList<Block>> blocks;

    public BlockedScenario() {
        super("Blocked", "You can only break blocks that others placed", new ItemBuilder(Material.DIAMOND_PICKAXE).name("Blocked").make());
        blocks = new HashMap<>();
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        if(!isEnabled()) return;

        ArrayList<Block> b;
        if(blocks.containsKey(e.getPlayer().getUniqueId())){
            b = blocks.get(e.getPlayer().getUniqueId());
        }else{
            b = new ArrayList<>();
        }
        b.add(e.getBlock());
        blocks.put(e.getPlayer().getUniqueId(), b);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if(!isEnabled()) return;

        if(blocks.containsKey(e.getPlayer().getUniqueId())){
            ArrayList<Block> b = blocks.get(e.getPlayer().getUniqueId());
            if(b.contains(e.getBlock())){
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatUtils.format(getPrefix() + "&cYou can only break other player's blocks!"));
            }
        }
    }
}
