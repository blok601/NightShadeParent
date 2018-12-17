package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.event.GameStartEvent;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class OneHundredHeartsScenario extends Scenario {
  public OneHundredHeartsScenario() {
    super("OneHundredHearts", "Players receive LOTS of health OwO", new ItemBuilder(Material.GOLDEN_APPLE).name("One Hundred Hearts").make());
  }

  public void onStart(GameStartEvent e) {
    if(!isEnabled()) return;
    UHCPlayer gamePlayer;
    for (Player player : Bukkit.getOnlinePlayers()) {
      gamePlayer = UHCPlayer.get(player.getUniqueId());
      if(gamePlayer == null) continue;
      if(gamePlayer.isSpectator()) continue;
      player.setMaxHealth(200);
      player.setHealth(200);
    }
  }
}
