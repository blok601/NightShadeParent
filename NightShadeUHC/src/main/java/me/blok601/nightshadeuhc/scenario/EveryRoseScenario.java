package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.events.GameStartEvent;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class EveryRoseScenario extends Scenario {
  public EveryRoseScenario() {
    super("EveryRose", "EVERRRYYYYY ROSEEE HAS ITS THORNSSSSSSS (Players get a Thorns 3 Golden Chestplate On game Start)", new ItemBuilder(Material.RED_ROSE).name("Every Rose").make());
  }
  @EventHandler
  public void onStart(GameStartEvent e){
    if(!isEnabled()){
      return;
    }
    ItemBuilder chestplate = new ItemBuilder(Material.GOLD_CHESTPLATE).enchantment(Enchantment.THORNS, 3);

    Bukkit.getOnlinePlayers().stream().filter(o -> !UHCPlayer.get(o.getUniqueId()).isSpectator()).forEach(player ->{
      player.getInventory().addItem(chestplate.make());
    });
  }

}
