package com.nightshadepvp.tournament.gui;

import com.nightshadepvp.core.gui.GuiBuilder;
import com.nightshadepvp.core.utils.ItemBuilder;
import com.nightshadepvp.tournament.entity.TPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 8/8/2018.
 */
public class StatsGUI {

    public StatsGUI(TPlayer target, Player toOpen) {
        GuiBuilder builder = new GuiBuilder();
        builder.name(target.getName() + "'s Stats");
        builder.rows(3);

        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemBuilder skullBuilder = new ItemBuilder(head).name("&e" + target.getName());

        ItemBuilder kills = new ItemBuilder(Material.DIAMOND_SWORD).name("&eMatches Won&8» &5" + target.getFightsWon());
        ItemBuilder deaths = new ItemBuilder(Material.GOLD_SWORD).name("&eMatches Lost&8» &5" + target.getFightsLost());
        ItemBuilder kd = new ItemBuilder(Material.IRON_SWORD).name("&eMatch W/L&8» &5" + target.getKdRatio() + "%");

        ItemBuilder gamesPlayed = new ItemBuilder(Material.BEDROCK).name("&eTournaments Played&8» &5" + target.getTournamentsPlayed());
        ItemBuilder wins = new ItemBuilder(Material.GOLDEN_APPLE).data(1).name("&eTournaments Won&8» &5" + target.getTournamentsWon());
        ItemBuilder wg = new ItemBuilder(Material.SIGN).name("&eTournament W/L&8» &5" + target.getWG() + "%");

        ItemBuilder matchHistory = new ItemBuilder(Material.PAPER).name("&eMatch History").lore("&bClick to view full match history").lore("&4&lCOMING SOON");

        builder.item(0, skullBuilder.make());
        builder.item(1, kills.make());
        builder.item(2, deaths.make());
        builder.item(3, kd.make());

        builder.item(6, wins.make());
        builder.item(7, gamesPlayed.make());
        builder.item(8, wg.make());
        builder.item(21, matchHistory.make());

        toOpen.openInventory(builder.make());
        toOpen.updateInventory();
    }
}
