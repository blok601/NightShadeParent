package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.event.CustomDeathEvent;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class DiamondLessScenario extends Scenario{


    public DiamondLessScenario() {
        super("Diamondless", "Upon mining a diamond ore, you will not receive the diamond. When a player dies, they will drop 1 diamond", new ItemBuilder(Material.DIAMOND_ORE).name(ChatUtils.format("Diamondless")).make());
    }

    @EventHandler
    public void onDeath(CustomDeathEvent e){
        if(!isEnabled()){
            return;
        }

        ItemStack diamond = new ItemStack(Material.DIAMOND, 1);

        e.getItems().add(diamond);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){

        if(!isEnabled()){
            return;
        }

        if(e.getBlock().getType().equals(Material.DIAMOND_ORE)) {
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR); //Lol wtf are you dropping here
        }
    }
}
