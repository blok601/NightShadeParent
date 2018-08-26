package me.blok601.nightshadeuhc.gui.leaderboards;

import me.blok601.nightshadeuhc.gui.GuiBuilder;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 7/4/2018.
 */
public class LeaderBoardMainGUI {

    public LeaderBoardMainGUI(Player player){
        GuiBuilder builder = new GuiBuilder();
        builder.name("&eLeaderboards");
        builder.rows(3);

        ItemStack wins = new ItemBuilder(Material.SIGN).name("&6Wins").lore("&cSee NightShadePvP's Top 10 Most Winning Players are.").make();
        ItemStack kills = new ItemBuilder(Material.DIAMOND_SWORD).name("&6Kills").lore("&cSee who NightShadePvP's Top 10 Killers are.").lore("&4&LCOMING SOON!").make();
        ItemStack allAround = new ItemBuilder(Material.PAPER).name("&6All Around").lore("&cSee who NightShadePvP's Top 10 UHC Players are.").lore("&4&lCOMING SOON!").make();


        builder.item(11, wins);
        builder.item(13, kills);
        builder.item(15, allAround);

        player.openInventory(builder.make());
    }

}
