package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.event.GameStartEvent;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

//Created By Ozzy, All by himself. July 20th, 2028
public class BomberScenario extends Scenario {
  public BomberScenario(){
    super("Bombers", "Mobs and Animals drop TNT. Everyone receives an unbreakable Flint and Steel on start.", new ItemBuilder(Material.TNT).name("Bombers").make());
  }

  @EventHandler
  public void on(EntityDeathEvent e){
    if(!isEnabled()) return;

    if(e.getEntity() instanceof Monster){
      e.getDrops().add(new ItemStack(Material.TNT, 3));
    }
    if(e.getEntity() instanceof Sheep) {
      e.getDrops().add(new ItemStack(Material.TNT, 2));
    }
    if(e.getEntity() instanceof Cow) {
      e.getDrops().add(new ItemStack(Material.TNT, 2));
    }
    if(e.getEntity() instanceof Pig) {
      e.getDrops().add(new ItemStack(Material.TNT, 2));
    }
    if(e.getEntity() instanceof Chicken) {
      e.getDrops().add(new ItemStack(Material.TNT, 2));
    }
    if(e.getEntity() instanceof Rabbit) {
      e.getDrops().add(new ItemStack(Material.TNT, 2));

    }
  }
  @EventHandler
  public void onStart(GameStartEvent e){
    if(!isEnabled()){
      return;
    }
    ItemBuilder FnS = new ItemBuilder(Material.FLINT_AND_STEEL);

    Bukkit.getOnlinePlayers().stream().filter(o -> !UHCPlayer.get(o.getUniqueId()).isSpectator()).forEach(player ->{
      player.getInventory().addItem(FnS.make());
    });
  }
  @EventHandler
  public void onBreak (PlayerItemBreakEvent e) {
    Player p = e.getPlayer();
    if (e.getBrokenItem().getType() == Material.FLINT_AND_STEEL) {
      p.getInventory().addItem(new ItemStack(Material.FLINT_AND_STEEL));
    }
  }
  }
