package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.event.CustomDeathEvent;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FastGetawayScenario extends Scenario {
  public FastGetawayScenario() {
    super("FastGetaway", "Players receive speed 2 for 30 seconds when the get a kill", new ItemBuilder(Material.RABBIT_FOOT).name("FastGetaway").make());
  }

  @EventHandler
  public void onDeath (CustomDeathEvent e) {
    if (!isEnabled()) {
      return;
    }
      e.getKiller().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*30, 1));
  }
}
