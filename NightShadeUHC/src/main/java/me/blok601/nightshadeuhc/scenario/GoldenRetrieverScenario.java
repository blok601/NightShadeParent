package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.event.CustomDeathEvent;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class GoldenRetrieverScenario extends Scenario{
  public GoldenRetrieverScenario() {
    super("Golden Retriever", "An Extra Golden Head is Dropped on Death", new ItemBuilder(Material.GOLDEN_APPLE).name("Golden Retriever").make());
  }

  @EventHandler
  public void onDeath (CustomDeathEvent e) {
    if (!isEnabled()) return;
    ItemStack apple = new ItemBuilder(Material.GOLDEN_APPLE).name(ChatUtils.format("&6Golden Head")).make();

    e.getItems().add(apple);
  }

}
