package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityCombustByEntityEvent;

public class ColdWeaponsScenario extends Scenario {

  public ColdWeaponsScenario() {
    super("ColdWeapons", "Fire Weapons are Disabled (They do not work)", new ItemBuilder(Material.PACKED_ICE).name("ColdWeapons").make());
  }

  @EventHandler
  public void onCombust (EntityCombustByEntityEvent e) {
    if (!isEnabled()) {
      return;
    }

    if (e.getCombuster() instanceof Arrow || e.getCombuster() instanceof Player) {
      e.setCancelled(true);
    }
  }
}
