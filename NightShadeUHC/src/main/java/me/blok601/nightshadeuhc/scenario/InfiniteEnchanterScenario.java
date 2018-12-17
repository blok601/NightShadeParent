package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import me.blok601.nightshadeuhc.event.GameStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 12/3/2017.
 */
public class InfiniteEnchanterScenario extends Scenario{


    public InfiniteEnchanterScenario() {
        super("Infinite Enchanter", "All players start with 128 Bookshelves, Infinite XP Levels, 64 Anvils and 64 Enchantment Tables", new ItemBuilder(Material.ENCHANTMENT_TABLE).name("Infinite Enchanter").make());
    }

    @EventHandler
    public void onStart(GameStartEvent e){

        if(!isEnabled()){
            return;
        }

        ItemStack bookshelves = new ItemBuilder(Material.BOOKSHELF).amount(64).make();
        ItemStack anvil = new ItemBuilder(Material.ENCHANTMENT_TABLE).amount(64).make();
        ItemStack tables = new ItemBuilder(Material.ANVIL).amount(64).make();
        ItemStack lapis = new ItemBuilder(Material.LAPIS_BLOCK).amount(64).make();

        UHCPlayer gamePlayer;
        for (Player player : Bukkit.getOnlinePlayers()){
            gamePlayer = UHCPlayer.get(player.getUniqueId());
            if(!gamePlayer.isSpectator()){
                player.getInventory().addItem(anvil);
                player.getInventory().addItem(tables);
                for (int i = 0; i < 3; i++){
                    player.getInventory().addItem(bookshelves);
                    player.getInventory().addItem(lapis);
                }
                player.setLevel(27000);
            }
        }
    }
}
