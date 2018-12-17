package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.event.GameStartEvent;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Blok on 6/21/2018.
 */
public class OneHealScenario extends Scenario {


    public OneHealScenario() {
        super("OneHeal", "Player's get a golden hoe that they can right click to heal themselves with once throughout the game", new ItemBuilder(Material.GOLD_HOE).name("OneHeal").make());
    }

    @EventHandler
    public void onGameStart(GameStartEvent e){
        if(!isEnabled()) return;

        ItemStack itemStack = new ItemBuilder(Material.GOLD_HOE).name("&eMagical Tool").lore("&6Right click to receive a full heal! Only works once in the game").make();

        UHCPlayer gamePlayer;
        for (Player player : Bukkit.getOnlinePlayers()){
            gamePlayer = UHCPlayer.get(player.getUniqueId());
            if(gamePlayer == null) continue;
            if(gamePlayer.isSpectator()) continue;
            player.getInventory().addItem(itemStack);
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e){
        if(!isEnabled()) return;

        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(e.getItem().getType() == Material.GOLD_HOE){
                ItemMeta meta = e.getItem().getItemMeta();
                if(meta.getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "Magical Tool")){
                    e.getPlayer().setHealth(e.getPlayer().getMaxHealth());
                    e.getPlayer().getInventory().remove(e.getItem());
                    e.getPlayer().getItemInHand().setType(Material.AIR);
                    e.getPlayer().sendMessage(ChatUtils.format(getPrefix() + "&eYou have redeemed your one heal!"));
                }
            }
        }
    }
}
