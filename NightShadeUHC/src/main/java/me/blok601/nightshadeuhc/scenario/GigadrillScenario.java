package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GigadrillScenario extends Scenario {
  public GigadrillScenario() {
    super("GigaDrill", "All tools that you craft are enchanted with Efficency 10 and Unbreaking 3", new ItemBuilder(Material.DIAMOND_PICKAXE).name("GigaDrill").make());
  }

  List<Material> types = new ArrayList<>(Arrays.asList(Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE,
          Material.WOOD_SPADE, Material.GOLD_SPADE, Material.IRON_SPADE, Material.STONE_SPADE, Material.DIAMOND_SPADE,
          Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.DIAMOND_AXE
  ));

  @EventHandler
  public void onCraft(PrepareItemCraftEvent e){

    if(!isEnabled()) return;

    if(types.contains(e.getRecipe().getResult().getType())){
      e.getInventory().setResult(new ItemBuilder(e.getRecipe().getResult()).enchantment(Enchantment.DIG_SPEED, 10).enchantment(Enchantment.DURABILITY, 3).
              make());
    }
  }
}
