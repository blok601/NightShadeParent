package me.blok601.nightshadeuhc.scenario;

import com.nightshadepvp.core.utils.ChatUtils;
import com.nightshadepvp.core.utils.ItemBuilder;
import me.blok601.nightshadeuhc.event.CustomDeathEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class GoldLessScenario extends Scenario{


    public GoldLessScenario() {
        super("Goldless", "When mining a gold ore, you will not get a gold ingot. When a player dies, they drop a golden head and 8 gold.", new ItemBuilder(Material.GOLD_ORE).name(ChatUtils.format("Goldless")).make());
    }

    @EventHandler
    public void onDeath(CustomDeathEvent e){
        if(!isEnabled()){
            return;
        }

        ItemStack gold = new ItemStack(Material.GOLD_INGOT, 8);
        ItemStack apple = new ItemBuilder(Material.GOLDEN_APPLE).name(ChatUtils.format("&6Golden Head")).make();


        e.getItems().add(gold);
        e.getItems().add(apple);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){

        if(!isEnabled()){
            return;
        }

        if (e.getBlock().getType() == Material.GOLD_ORE) {
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
            ItemStack stack = new ItemStack(Material.AIR);
            e.getPlayer().getWorld().dropItemNaturally(e.getBlock().getLocation(), stack);
        }
    }
}
