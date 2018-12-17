package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.event.GameStartEvent;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class CreeperPongScenario extends Scenario {
  public CreeperPongScenario() {
    super("CreeperPong", "Players get a Knockback 10 stick, and 20 Creeper Eggs", new ItemBuilder(Material.STICK).name("CreeperPong").make());
  }
  @EventHandler
  public void onStart(GameStartEvent e){
    if(!isEnabled()){
      return;
    }
    int typeID = 50;
    ItemBuilder stick = new ItemBuilder(Material.STICK).enchantment(Enchantment.KNOCKBACK, 10);

    Bukkit.getOnlinePlayers().stream().filter(o -> !UHCPlayer.get(o.getUniqueId()).isSpectator()).forEach(player ->{
      player.getInventory().addItem(stick.make());
      player.getInventory().addItem(new ItemStack(Material.MONSTER_EGG, 20, (short) typeID));
    });
  }

}
