package me.blok601.nightshadeuhc.gui.leaderboards;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.gui.GuiBuilder;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;

public class OreLeaderboard {

    public OreLeaderboard(Player player) {
        GuiBuilder builder = new GuiBuilder();
        builder.name("&eTop Mined Ores").rows(1);

        //Sort ores now
        ArrayList<UHCPlayer> players = new ArrayList<>(UHCPlayerColl.get().getAllPlaying());
        players.sort(Comparator.comparing(UHCPlayer::getOresMined).reversed());

        ItemStack skull = null;
        ItemBuilder newSkull = null;
        UHCPlayer uhcPlayer;

        for (int i = 0; i < 9; i++){
            uhcPlayer = players.get(i);
            skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            newSkull = new ItemBuilder(skull);
            newSkull.skullOwner(uhcPlayer.getName());
            newSkull.name(uhcPlayer.getName());
            newSkull.lore("&bDiamonds Mined&8» &e" + uhcPlayer.getCurrentDiamondsMined());
            newSkull.lore("&6Gold Mined&8» &e" + uhcPlayer.getCurrentGoldMined());
            newSkull.lore("&fIron Mined&8» &e" + uhcPlayer.getIronMined());
            newSkull.lore("&9Lapis Mined&8» &e" + uhcPlayer.getCurrentLapisMined());
            newSkull.lore("&&eTotal Ores Mined&8» &e" + uhcPlayer.getCurrentOresMined());
            builder.item(i, newSkull.make());
            i++;
        }

        player.openInventory(builder.make());
        player.updateInventory();
    }
}
