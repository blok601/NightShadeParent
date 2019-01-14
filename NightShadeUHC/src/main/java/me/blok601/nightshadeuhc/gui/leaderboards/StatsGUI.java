package me.blok601.nightshadeuhc.gui.leaderboards;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.gui.GuiBuilder;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.DecimalFormat;

/**
 * Created by Blok on 7/4/2018.
 */
public class StatsGUI {

    public StatsGUI(UHCPlayer target, Player toOpen){
        GuiBuilder builder = new GuiBuilder();
        builder.name(target.getName() + "'s Stats");
        builder.rows(6);

        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
        skullMeta.setOwner(target.getName());
        ItemBuilder skullBuilder = new ItemBuilder(head).name("&e" + target.getName());

        ItemBuilder uhcLabel = new ItemBuilder(Material.NAME_TAG).name("&eUHC Stats&8»");


        ItemBuilder kills = new ItemBuilder(Material.DIAMOND_SWORD).name("&eKills &8» &5" + target.getKills());
        ItemBuilder deaths = new ItemBuilder(Material.GOLD_SWORD).name("&eDeaths &8» &5" + target.getDeaths());
        ItemBuilder kd = new ItemBuilder(Material.IRON_SWORD).name("&eK/D &8» &5" + target.getKD());

        ItemBuilder gamesPlayed = new ItemBuilder(Material.BEDROCK).name("&eGames Played &8» &5" + target.getGamesPlayed());
        ItemBuilder wins = new ItemBuilder(Material.GOLDEN_APPLE).data(1).name("&eGames Won &8» &5" + target.getGamesWon());
        ItemBuilder wg = new ItemBuilder(Material.SIGN).name("&eW/G& 8» &5" + target.getWG());

        ItemBuilder blocksBroken = new ItemBuilder(Material.COBBLESTONE).name("&eBlocks Broken &8» &5" + target.getBlocksMined());
        ItemBuilder coal = new ItemBuilder(Material.COAL_ORE).name("&eCoal Mined &8» &5" + target.getCoalMined());
        ItemBuilder diamonds = new ItemBuilder(Material.DIAMOND_ORE).name("&eDiamonds Mined &8» &5" + target.getDiamondsMined());
        ItemBuilder gold = new ItemBuilder(Material.GOLD_ORE).name("&eGold Mined &8» &5" + target.getGoldMined());
        ItemBuilder iron = new ItemBuilder(Material.IRON_ORE).name("&eIron Mined &8» &5" + target.getIronMined());
        ItemBuilder lapis = new ItemBuilder(Material.LAPIS_ORE).name("&eLapis Mined &8» &5" + target.getLapisMined());
        ItemBuilder emerald = new ItemBuilder(Material.EMERALD_ORE).name("&eEmeralds Mined &8» &5" + target.getEmeraldsMined());
        ItemBuilder oresBroken = new ItemBuilder(Material.QUARTZ_ORE).name("&eOres Mined& 8» &5" + target.getOresMined());
        ItemBuilder rating = new ItemBuilder(Material.PAPER).name("&eRating &8» &5" + new DecimalFormat("#.##").format(target.getPointsAverage()));

        ItemBuilder arenaLabel = new ItemBuilder(Material.NAME_TAG).name("&eArena Stats &8»");
        ItemBuilder arenaKills = new ItemBuilder(Material.DIAMOND_SWORD).name("&eArena Stats &8»")
                .lore("&5Kills &8» &b" + target.getArenaKills())
                .lore(" ")
                .lore("&5Deaths &8» &b" + target.getArenaDeaths())
                .lore(" ")
                .lore("&5K/D &8» &b" + target.getArenaKDR());
        ItemBuilder arenaPvP = new ItemBuilder(Material.IRON_SWORD).name("&eArena PvP Stats &8»")
                .lore("&5Arena Sword Swings &8» &b" + target.getArenaSwordSwings())
                .lore(" ")
                .lore("&5Arena Sword Hits &8» &b" + target.getArenaSwordHits())
                .lore(" ")
                .lore("&5Arena Highest KillStreak &8» &b" + target.getHighestArenaKillStreak());
        ItemBuilder arenaHeals = new ItemBuilder(Material.GOLDEN_APPLE).name("&eArena Heals &8»")
                .lore("&5Gapples Eaten &8» &b" + target.getArenaGapplesEaten());
        ItemBuilder bow = new ItemBuilder(Material.BOW).name("&eArena Bow")
                .lore("&5Arena Bow Attempts &8» &b" + target.getArenaBowAttempts())
                .lore(" ")
                .lore("&5Arena Bow Hits &8» &b" + target.getArenaBowHits());
        ItemBuilder sessions = new ItemBuilder(Material.SIGN).name("&eArena Sessions")
                .lore("&6Click to view past arena sessions");


        builder.item(4, skullBuilder.make());
        builder.item(18, uhcLabel.make());
        builder.item(19, kills.make());
        builder.item(20, deaths.make());
        builder.item(21, kd.make());
        builder.item(22, gamesPlayed.make());
        builder.item(24, wins.make());
        builder.item(23, wg.make());
        builder.item(26, rating.make());
        builder.item(28, blocksBroken.make());
        builder.item(29, diamonds.make());
        builder.item(30, iron.make());
        builder.item(31, gold.make());
        builder.item(32, coal.make());
        builder.item(33, lapis.make());
        builder.item(34, emerald.make());
        builder.item(35, oresBroken.make());
        builder.item(31, rating.make());
        builder.item(45, arenaLabel.make());
        builder.item(46, arenaKills.make());
        builder.item(47, arenaPvP.make());
        builder.item(48, arenaHeals.make());
        builder.item(49, bow.make());
        builder.item(50, sessions.make());
        if (NSPlayer.get(toOpen).hasRank(Rank.TRIAL)) {
            //Add the edit and clear options
            ItemBuilder clear = new ItemBuilder(Material.LAVA_BUCKET).name("&4Clear Stats").lore("&4&lWARNING: This can not be undone!");
            builder.item(8, clear.make());

            ItemBuilder modify = new ItemBuilder(Material.PAPER).name("&4Edit Stats").lore("&6Click to edit &b" + target.getName() + "'s &6stats");
            builder.item(0, modify.make());

            ItemBuilder clearArena = new ItemBuilder(Material.LAVA_BUCKET).name("&4Clear Arena Stats").lore("&4&lWARNING: This can not be undone!");
            builder.item(53, clearArena.make());

            ItemBuilder modifyArena = new ItemBuilder(Material.PAPER).name("&4Edit Arena Stats").lore("&6Click to edit &b" + target.getName() + "'s &6arena stats");
            builder.item(52, modifyArena.make());
        }

        toOpen.openInventory(builder.make());
        toOpen.updateInventory();
    }

}
