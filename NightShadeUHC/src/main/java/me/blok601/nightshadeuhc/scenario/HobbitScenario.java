package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.events.GameStartEvent;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HobbitScenario extends Scenario {
  public HobbitScenario(){
    super("Hobbit", " At the start of the game, everyone will be given a golden nugget(which signifies as your own ring). Once you right click it, it will disappear from your inventory and will give invisibility for 30 seconds.", new ItemBuilder(Material.GOLD_NUGGET).name("Hobbit").make());
  }
  @EventHandler
  public void onStart(GameStartEvent e) {
    if(!isEnabled()) return;
    ItemBuilder hobbit = new ItemBuilder(Material.GOLD_NUGGET).name(ChatColor.YELLOW + "The Ring").lore("This is the Hobbit Ring! Rick click it to become invis for 30 seconds!");
    Bukkit.getOnlinePlayers().stream().filter(o -> !UHCPlayer.get(o.getUniqueId()).isSpectator()).forEach(player -> {
      player.getInventory().addItem(hobbit.make());
    });
  }
  @EventHandler
  public void onClick(PlayerInteractEvent e){
    if(!isEnabled()) return;

    if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
      if(e.getItem().getType() == Material.GOLD_NUGGET){
        ItemMeta meta = e.getItem().getItemMeta();
        if(meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "The Ring")) {
          e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20*30, 0));
          e.getPlayer().getInventory().remove(e.getItem());
          e.getPlayer().sendMessage(ChatUtils.format(getPrefix() + "&eYou hath Hobbit - ed?"));
        }
      }
    }
  }

}
