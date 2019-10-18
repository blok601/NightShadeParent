package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

public class DragonRushScenario extends Scenario{
  public DragonRushScenario() {
    super("DragonRush", "The First player to Kill the ender dragon wins.", new ItemBuilder(Material.DRAGON_EGG).name(ChatUtils.format("Dragon Rush")).make());
  }
  @EventHandler
  public void onComplete(EntityDeathEvent e) {
    if (!isEnabled()) return;
    if (e.getEntity() instanceof EnderDragon) {
      Bukkit.broadcastMessage(ChatUtils.format(getPrefix() + "&c" + e.getEntity().getKiller().getName() + "Has Killed the Ender Dragon! Their team wins!"));
    }
  }

}
