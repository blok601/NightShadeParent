package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class SkycleanScenario extends Scenario{

    public SkycleanScenario() {
        super("Skyclean", "Everything is pre-smelted, as well as glass will drop from sand, and snow blocks from snow", new ItemBuilder(Material.GLASS).name("Skyclean").make());
    }



    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if(!isEnabled()){
            return;
        }

        //Enabled
        Block b = e.getBlock();
        Player p = e.getPlayer();
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
        }else if(b.getType().equals(Material.SAND)){
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
            p.getInventory().addItem(new ItemStack(Material.GLASS, 1));
        }else if(b.getType().equals(Material.SNOW)){
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
            p.getInventory().addItem(new ItemStack(Material.SNOW_BLOCK, 1));
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e){
        if(!isEnabled()){
            return;
        }

        Entity en = e.getEntity();
        if(en.getType() == EntityType.PIG){
            e.getDrops().clear();
            en.getLocation().getWorld().dropItemNaturally(en.getLocation().add(1, 0, 0), new ItemStack(Material.GRILLED_PORK, 3));
        }else if(en.getType() == EntityType.COW){
            e.getDrops().clear();
            en.getLocation().getWorld().dropItemNaturally(en.getLocation().add(1, 0, 0), new ItemStack(Material.COOKED_BEEF, 3));
            en.getLocation().getWorld().dropItemNaturally(en.getLocation().add(1, 0, 0), new ItemStack(Material.LEATHER, 1));
        }else if(en.getType() == EntityType.SHEEP){
            e.getDrops().clear();
            en.getLocation().getWorld().dropItemNaturally(en.getLocation().add(1, 0, 0), new ItemStack(Material.COOKED_BEEF, 3));
        }else if(en.getType() == EntityType.CHICKEN){
            e.getDrops().clear();
            en.getLocation().getWorld().dropItemNaturally(en.getLocation().add(1, 0, 0), new ItemStack(Material.COOKED_CHICKEN, 3));
            en.getLocation().getWorld().dropItemNaturally(en.getLocation().add(1, 0, 0), new ItemStack(Material.FEATHER, 2));
        }
    }
}
