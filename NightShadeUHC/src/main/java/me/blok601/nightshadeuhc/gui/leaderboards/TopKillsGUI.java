package me.blok601.nightshadeuhc.gui.leaderboards;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.gui.GuiBuilder;
import me.blok601.nightshadeuhc.stats.handler.StatsHandler;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Blok on 7/4/2018.
 */
public class TopKillsGUI {

    public TopKillsGUI(Player player){
        GuiBuilder builder = new GuiBuilder();
        builder.name("&eTop 10 Killers");
        builder.rows(6);

        List<UHCPlayer> kills = StatsHandler.getInstance().getKills().subList(0, 10);

        int[] assign = {13, 21, 23, 29, 31, 33, 37, 39, 41, 43};
        int index = 0;

        ItemStack skull = null;
        ItemBuilder newSkull = null;

        for (UHCPlayer uhcPlayer : kills){
            skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            newSkull = new ItemBuilder(skull);
            newSkull.skullOwner(uhcPlayer.getName());
            newSkull.name("&e" + (index + 1) + ".&5 " + uhcPlayer.getName());
            newSkull.lore("&eKills&8» &6" + uhcPlayer.getKills());
            builder.item(assign[index], newSkull.make());
            index++;
        }

        player.openInventory(builder.make());
        player.updateInventory();

    }
}
