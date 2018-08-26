package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 3/20/2018.
 */
public class SlutCleanScenario extends Scenario{


    public SlutCleanScenario() {
        super("SlutCleanScenario", "Ores drop smelted if you're not wearing armor", new ItemBuilder(Material.BEACON).name("SlutCleanScenario").make());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){

        if(!isEnabled()) return;

        Player p = e.getPlayer();
        Block b = e.getBlock();

        if(p.getInventory().getArmorContents() == null){
            Location clone = new Location(b.getWorld(),  b.getLocation().getBlockX() + 0.5D, b.getLocation().getBlockY(),  b.getLocation().getBlockZ() + 0.5D); //XP ORB

            if(e.getBlock().getType().equals(Material.GOLD_ORE)){
                e.setCancelled(true);
                b.setType(Material.AIR);
                e.getPlayer().getInventory().addItem(new ItemStack(Material.GOLD_INGOT, 1));
                ((ExperienceOrb)b.getWorld().spawn(clone, ExperienceOrb.class)).setExperience(3);
            }else if(b.getType().equals(Material.IRON_ORE)){
                e.setCancelled(true);
                e.getBlock().setType(Material.AIR);
                p.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 1));
                ((ExperienceOrb)b.getWorld().spawn(clone, ExperienceOrb.class)).setExperience(3);
            }
        }
    }
}
