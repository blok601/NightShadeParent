package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import me.blok601.nightshadeuhc.event.GameStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 2/17/2018.
 */
public class GoneFishinScenario extends Scenario{


    public GoneFishinScenario() {
        super("GoneFishin'", "Each player starts with an Unbreaking 100 and Luck of the Sea 250 fishing rod along with 20 anvils.", new ItemBuilder(Material.FISHING_ROD).name("GoneFishin'").make());
    }

    @EventHandler
    public void on(GameStartEvent e){
        if(!isEnabled()){
            return;
        }


        Bukkit.getOnlinePlayers().stream().filter(o -> !UHCPlayer.get(o.getUniqueId()).isSpectator()).forEach(player ->{
            player.getInventory().addItem(new ItemStack(Material.ANVIL, 20));
            player.getInventory().addItem(new ItemBuilder(Material.FISHING_ROD).enchantment(Enchantment.DURABILITY, 100).enchantment(Enchantment.LUCK, 250).enchantment(Enchantment.LURE, 3).make());
            player.setLevel(20000);
        });
    }
    @EventHandler
    public void onCraft(CraftItemEvent e){
        if (!isEnabled()) return;
        if(e.getWhoClicked() == null) return;
        if(!(e.getWhoClicked() instanceof Player)) return;
        if(e.getInventory() == null || e.getClickedInventory() == null) return;
        if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR)return;

        Player p = (Player) e.getWhoClicked();

        if(e.getRecipe().getResult().getType() == Material.ENCHANTMENT_TABLE){
            e.setCancelled(true);
            p.closeInventory();
            p.sendMessage(ChatUtils.format(getPrefix() + "You cannot craft an Enchantment Table in Gone Fishing!"));

        }
    }
}
