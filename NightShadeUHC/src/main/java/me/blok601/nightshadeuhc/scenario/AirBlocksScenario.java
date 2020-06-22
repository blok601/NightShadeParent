package me.blok601.nightshadeuhc.scenario;

import com.google.common.collect.Lists;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;

public class AirBlocksScenario extends Scenario{

    private ArrayList<Block> toDecay;

    public AirBlocksScenario() {
        super("Stairway to Heaven", "Blocks can be placed in the air. Credit: AcidViper and Cow", new ItemBuilder(Material.LEAVES).name("Stairway to Heaven").make());
        this.toDecay = Lists.newArrayList();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(!isEnabled()) return;
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR))  {
            if (e.getPlayer().getItemInHand().getType().isBlock()) {

                HashSet<Material> transparent = new HashSet<Material>();
                transparent.add(Material.AIR);
                transparent.add(Material.STATIONARY_WATER);
                Block block = e.getPlayer().getTargetBlock(transparent, 3);
                block.setType(e.getPlayer().getItemInHand().getType());
                block.setData(e.getPlayer().getItemInHand().getData().getData());

                toDecay.add(block);

                e.getPlayer().getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getType(), 15);

                if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
                    if (e.getPlayer().getItemInHand().getAmount() == 1) {
                        e.getPlayer().setItemInHand(new ItemStack(Material.AIR, 0));
                    }
                    e.getPlayer().getItemInHand().setAmount(e.getPlayer().getItemInHand().getAmount() - 1);
                }

            }
        }
    }

    @EventHandler
    public void onDecay(LeavesDecayEvent e) {
        if(!isEnabled()) return;
        if (toDecay.contains(e.getBlock())) {
            e.setCancelled(true);
        }
    }
}
