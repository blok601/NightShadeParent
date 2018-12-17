package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.event.GameStartEvent;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class ChickenScenario extends Scenario {
  public ChickenScenario() {
    super("Chicken", "Everyone starts on half a heart, with a god apple.", new ItemBuilder(Material.RAW_CHICKEN).name("Chicken").make());
  }
  @EventHandler
  public void onStart(GameStartEvent e) {
    if (!isEnabled()) {
      return;
    }
    Bukkit.getOnlinePlayers().stream().filter(o -> !UHCPlayer.get(o.getUniqueId()).isSpectator()).forEach(player ->{
      player.setHealth(1.0);
      player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1, (short)1));
    });
    }

}
