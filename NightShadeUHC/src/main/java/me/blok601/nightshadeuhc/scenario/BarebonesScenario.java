package me.blok601.nightshadeuhc.scenario;

import com.nightshadepvp.core.utils.ChatUtils;
import com.nightshadepvp.core.utils.ItemBuilder;
import me.blok601.nightshadeuhc.event.CustomDeathEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 11/11/2017.
 */
public class BarebonesScenario extends Scenario{


    public BarebonesScenario() {
        super("Barebones", "Iron is the highest tier you can obtain through gearing up. When a player dies, they will drop 1 diamond, 1 golden apple, 32 arrows, and 2 string. You cannot craft an enchantment table, anvil, or golden apple.", new ItemBuilder(Material.BONE).name(ChatUtils.format("Barebones")).make());
    }

    @EventHandler
    public void onDeath(CustomDeathEvent e){
        if(!isEnabled()){
            return;
        }

        ItemStack diamond = new ItemStack(Material.DIAMOND, 1);
        ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE);
        ItemStack arrows = new ItemStack(Material.ARROW, 32);
        ItemStack string = new ItemStack(Material.STRING, 2);

        e.getItems().add(diamond);
        e.getItems().add(gapple);
        e.getItems().add(arrows);
        e.getItems().add(string);
    }


    @EventHandler
    public void onCraft(PrepareItemCraftEvent e){

        if(!isEnabled()){
            return;

        }

        if(e.getRecipe().getResult().getType() == Material.ENCHANTMENT_TABLE) {
            e.getInventory().setResult(new ItemStack(Material.AIR));
        }

        if(e.getRecipe().getResult().getType() == Material.ANVIL){
            e.getInventory().setResult(new ItemStack(Material.AIR));
        }

        if(e.getRecipe().getResult().getType() == Material.GOLDEN_APPLE){
            e.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){

        if(!isEnabled()){
            return;
        }

        if(e.getBlock().getType() == Material.EMERALD_ORE){
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
            ItemStack stack = new ItemStack(Material.IRON_INGOT);
            e.getPlayer().getWorld().dropItemNaturally(e.getBlock().getLocation(), stack);
        }else if(e.getBlock().getType() == Material.DIAMOND_ORE){
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
            ItemStack stack = new ItemStack(Material.IRON_INGOT);
            e.getPlayer().getWorld().dropItemNaturally(e.getBlock().getLocation(), stack);
        }else if(e.getBlock().getType() == Material.GOLD_ORE){
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
            ItemStack stack = new ItemStack(Material.IRON_INGOT);
            e.getPlayer().getWorld().dropItemNaturally(e.getBlock().getLocation(), stack);
        }
    }
}
