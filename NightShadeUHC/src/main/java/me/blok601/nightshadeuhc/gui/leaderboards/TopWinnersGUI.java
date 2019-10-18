package me.blok601.nightshadeuhc.gui.leaderboards;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.gui.GuiBuilder;
import me.blok601.nightshadeuhc.stat.handler.StatsHandler;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Blok on 7/4/2018.
 */
public class TopWinnersGUI {

    public TopWinnersGUI(Player player){
        GuiBuilder builder = new GuiBuilder();
        builder.name("&eTop 10 Winners");
        builder.rows(6);

        int[] assign = {4, 12, 14, 20, 22, 24, 28, 30, 32, 34};
        int index = 0;

        ItemStack skull = null;
        ItemBuilder newSkull = null;

        List<UHCPlayer> wins = StatsHandler.getInstance().getWinners().subList(0, 10);

        for (UHCPlayer uhcPlayer : wins){
            skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            newSkull = new ItemBuilder(skull);
            newSkull.skullOwner(uhcPlayer.getName());
            newSkull.name("&e" + (index + 1) + ".&5 " + uhcPlayer.getName());
            newSkull.lore("&eWins&8» &6" + uhcPlayer.getGamesWon());
            builder.item(assign[index], newSkull.make());
            index++;
        }

        ItemBuilder back = new ItemBuilder(Material.ARROW).name("&cBack");
        builder.item(45, back.make());


        player.openInventory(builder.make());
        player.updateInventory();

    }

}
