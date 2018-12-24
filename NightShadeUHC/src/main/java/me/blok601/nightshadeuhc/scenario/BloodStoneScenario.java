package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class BloodStoneScenario extends Scenario{


  public BloodStoneScenario() {
    super("Blood Stone", "When a player mines stone, they lose half a heart", new ItemBuilder(Material.STONE).name("Blood Stone").make());
  }

  @EventHandler
  public void onBreak(BlockBreakEvent e){

    if(!isEnabled()) return;

    if (e.getBlock().getType() == Material.STONE){
      e.getPlayer().damage(1.0);
      e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BAT_DEATH, 3, 3);
    }
  }
}