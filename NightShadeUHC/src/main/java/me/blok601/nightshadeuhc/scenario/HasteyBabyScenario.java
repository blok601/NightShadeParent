package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Blok on 1/28/2018.
 */
public class HasteyBabyScenario extends Scenario{


    public HasteyBabyScenario() {
        super("Hastey Baby", "All tools are enchanted with efficieny 1", new ItemBuilder(Material.WOOD_PICKAXE).enchantment(Enchantment.DIG_SPEED, 1).name("Hastey Baby").make());
    }


    List<Material> types = new ArrayList<>(Arrays.asList(Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE,
            Material.WOOD_SPADE, Material.GOLD_SPADE, Material.IRON_SPADE, Material.STONE_SPADE, Material.DIAMOND_SPADE,
            Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.DIAMOND_AXE
    ));

    @EventHandler
    public void onCraft(PrepareItemCraftEvent e){

        if(!isEnabled()) return;

        if(types.contains(e.getRecipe().getResult().getType())){
            e.getInventory().setResult(new ItemBuilder(e.getRecipe().getResult()).enchantment(Enchantment.DIG_SPEED, 1).enchantment(Enchantment.DURABILITY, 2).
                    make());
        }
    }
}
