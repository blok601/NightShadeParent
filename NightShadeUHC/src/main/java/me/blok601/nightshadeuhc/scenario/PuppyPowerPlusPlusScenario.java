package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.event.GameStartEvent;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class PuppyPowerPlusPlusScenario extends Scenario {
  int typeID = 95;


  public PuppyPowerPlusPlusScenario() {
    super("Puppy Power ++", "Everyone starts with 5 stacks of Bones, 5 stacks of Rotten Flesh, and 5 stacks of Wolf Spawn Eggs\n", new ItemBuilder(Material.RED_ROSE).name("Puppy Power ++").make());
  }

  @EventHandler
  public void onStart(GameStartEvent e){
    if(!isEnabled()){
      return;
    }

    Bukkit.getOnlinePlayers().stream().filter(o -> !UHCPlayer.get(o.getUniqueId()).isSpectator()).forEach(player ->{
      player.getInventory().addItem(new ItemStack(Material.BONE, 320));
      player.getInventory().addItem(new ItemStack(Material.ROTTEN_FLESH, 320));
      player.getInventory().addItem(new ItemStack(Material.MONSTER_EGG, 320, (short) typeID));
    });
  }

}