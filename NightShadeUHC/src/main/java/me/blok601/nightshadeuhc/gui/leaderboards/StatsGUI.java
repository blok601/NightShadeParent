package me.blok601.nightshadeuhc.gui.leaderboards;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.gui.GuiBuilder;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 7/4/2018.
 */
public class StatsGUI {

    public StatsGUI(UHCPlayer target, Player toOpen){
        GuiBuilder builder = new GuiBuilder();
        builder.name(target.getName() + "'s Stats");
        builder.rows(6);

        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemBuilder skullBuilder = new ItemBuilder(head).name("&e" + target.getName());

        ItemBuilder kills = new ItemBuilder(Material.DIAMOND_SWORD).name("&eKills&8» &5" + target.getKills());
        ItemBuilder deaths = new ItemBuilder(Material.GOLD_SWORD).name("&eDeaths&8» &5" + target.getDeaths());
        ItemBuilder kd = new ItemBuilder(Material.IRON_SWORD).name("&eK/D&8» &5" + target.getKD());

        ItemBuilder gamesPlayed = new ItemBuilder(Material.BEDROCK).name("&eGames Played&8» &5" + target.getGamesPlayed());
        ItemBuilder wins = new ItemBuilder(Material.GOLDEN_APPLE).data(1).name("&eGames Won&8» &5" + target.getGamesWon());
        ItemBuilder wg = new ItemBuilder(Material.SIGN).name("&eW/G&8» &5" + target.getWG());

        ItemBuilder blocksBroken = new ItemBuilder(Material.COBBLESTONE).name("&eBlocks Broken&8» &5" + target.getBlocksMined());
        ItemBuilder coal = new ItemBuilder(Material.COAL_ORE).name("&eCoal Mined&8» &5" + target.getCoalMined());
        ItemBuilder diamonds = new ItemBuilder(Material.DIAMOND_ORE).name("&eDiamonds Mined&8» &5" + target.getDiamondsMined());
        ItemBuilder gold = new ItemBuilder(Material.GOLD_ORE).name("&eGold Mined&8» &5" + target.getGoldMined());
        ItemBuilder iron = new ItemBuilder(Material.IRON_ORE).name("&eIron Mined&8» &5" + target.getIronMined());
        ItemBuilder lapis = new ItemBuilder(Material.LAPIS_ORE).name("&eLapis Mined&8» &5" + target.getLapisMined());
        ItemBuilder emerald = new ItemBuilder(Material.EMERALD_ORE).name("&eEmeralds Mined&8» &5" + target.getEmeraldsMined());
        ItemBuilder oresBroken = new ItemBuilder(Material.QUARTZ_ORE).name("&eOres Mined&8» &5" + target.getOresMined());

        //ItemBuilder rating = new ItemBuilder(Material.PAPER).name("&eRating&8» &5" + new DecimalFormat("#.##").format(target.getPointsAverage()))/*.lore(Place: place)*/;

        builder.item(0, skullBuilder.make());
        builder.item(1, kills.make());
        builder.item(10, deaths.make());
        builder.item(19, kd.make());
        builder.item(3, gamesPlayed.make());
        builder.item(12, wins.make());
        builder.item(21, wg.make());
        builder.item(5, blocksBroken.make());
        builder.item(6, coal.make());
        builder.item(7, diamonds.make());
        builder.item(8, gold.make());
        builder.item(14, oresBroken.make());
        builder.item(15, iron.make());
        builder.item(16, lapis.make());
        builder.item(17, emerald.make());
        //builder.item(40, rating.make());

        toOpen.openInventory(builder.make());
        toOpen.updateInventory();
    }

}
