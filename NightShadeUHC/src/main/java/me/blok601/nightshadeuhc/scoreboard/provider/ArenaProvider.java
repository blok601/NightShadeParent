package me.blok601.nightshadeuhc.scoreboard.provider;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.entity.object.ArenaSession;
import me.blok601.nightshadeuhc.scoreboard.SidebarEntry;
import me.blok601.nightshadeuhc.scoreboard.SidebarEntry;
import me.blok601.nightshadeuhc.scoreboard.SidebarProvider;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Blok on 12/24/2018.
 */
public class ArenaProvider implements SidebarProvider {

    /**
     * Gets the current Sidebar lines of the Scoreboard
     *
     * @param p
     * @return the Scoreboard Sidebar lines
     */
    @Override
    public List<SidebarEntry> getLines(Player p) {
        ArrayList<SidebarEntry> lines = new ArrayList<>();
        UHCPlayer uhcPlayer = UHCPlayer.get(p);
        lines.add(new SidebarEntry(ChatUtils.format("&f&m--------------" + ChatColor.AQUA.toString())));
        lines.add(new SidebarEntry(ChatUtils.format("&fPlayers: &b" + UHCPlayerColl.get().getAllOnline().stream().filter(UHCPlayer::isInArena).count())));
        lines.add(new SidebarEntry(ChatUtils.format("&f&m--------------" + ChatColor.BLUE.toString())));
        lines.add(new SidebarEntry(ChatUtils.format("&bYour Stats:")));
        lines.add(new SidebarEntry(ChatUtils.format("&fKills: &b" + uhcPlayer.getArenaKills())));
        lines.add(new SidebarEntry(ChatUtils.format("&fDeaths: &b" + uhcPlayer.getArenaDeaths())));
        lines.add(new SidebarEntry(ChatUtils.format("&fHighest KillStreak: &b" + uhcPlayer.getHighestArenaKillStreak())));
        lines.add(new SidebarEntry(ChatUtils.format("&fK/DR: &b" + ArenaSession.DECIMAL_FORMAT.format(uhcPlayer.getArenaKDR()))));
        lines.add(new SidebarEntry(ChatUtils.format("&f&m--------------" + ChatColor.DARK_BLUE.toString())));
        lines.add(new SidebarEntry(ChatUtils.format("&bCurrent Session:")));
        lines.add(new SidebarEntry(ChatUtils.format("&fKills: &b" + uhcPlayer.getArenaSession().getKills())));
        lines.add(new SidebarEntry(ChatUtils.format("&fDeaths: &b" + uhcPlayer.getArenaSession().getDeaths())));
        lines.add(new SidebarEntry(ChatUtils.format("&fKillStreak: &b" + uhcPlayer.getArenaSession().getKillstreak())));
        lines.add(new SidebarEntry(ChatUtils.format("&f&m--------------")));
        lines.add(new SidebarEntry(ChatUtils.format("&bdiscord.nightshadepvp.com")));
        return lines;
    }
}
