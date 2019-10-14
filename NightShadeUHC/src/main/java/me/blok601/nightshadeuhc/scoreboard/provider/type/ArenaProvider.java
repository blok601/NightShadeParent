package me.blok601.nightshadeuhc.scoreboard.provider.type;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.entity.object.ArenaSession;
import me.blok601.nightshadeuhc.scoreboard.ScoreboardProvider;
import me.blok601.nightshadeuhc.scoreboard.ScoreboardText;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Blok on 12/24/2018.
 */
public class ArenaProvider extends ScoreboardProvider {

    @Override
    public String getTitle(Player p) {
        return ChatUtils.format("&5Arena");
    }

    /**
     * Gets the current Sidebar lines of the Scoreboard
     *
     * @param p
     * @return the Scoreboard Sidebar lines
     */
    @Override
    public List<ScoreboardText> getLines(Player p) {
        ArrayList<ScoreboardText> lines = new ArrayList<>();
        UHCPlayer uhcPlayer = UHCPlayer.get(p);
        lines.add(new ScoreboardText(ChatUtils.format("&f&m--------------")));
        lines.add(new ScoreboardText(ChatUtils.format("&fPlayers: &b" + UHCPlayerColl.get().getAllOnline().stream().filter(UHCPlayer::isInArena).count())));
        lines.add(new ScoreboardText(ChatUtils.format("&f&m--------------")));
        lines.add(new ScoreboardText(ChatUtils.format("&bYour Stats:")));
        lines.add(new ScoreboardText(ChatUtils.format("&fKills: &b" + uhcPlayer.getArenaKills())));
        lines.add(new ScoreboardText(ChatUtils.format("&fDeaths: &b" + uhcPlayer.getArenaDeaths())));
        lines.add(new ScoreboardText(ChatUtils.format("&fHighest KillStreak: &b" + uhcPlayer.getHighestArenaKillStreak())));
        lines.add(new ScoreboardText(ChatUtils.format("&fK/DR: &b" + ArenaSession.DECIMAL_FORMAT.format(uhcPlayer.getArenaKDR()))));
        lines.add(new ScoreboardText(ChatUtils.format("&f&m--------------")));
        lines.add(new ScoreboardText(ChatUtils.format("&bCurrent Session:")));
        lines.add(new ScoreboardText(ChatUtils.format("&fKills: &b" + uhcPlayer.getArenaSession().getKills())));
        lines.add(new ScoreboardText(ChatUtils.format("&fDeaths: &b" + uhcPlayer.getArenaSession().getDeaths())));
        lines.add(new ScoreboardText(ChatUtils.format("&fKillStreak: &b" + uhcPlayer.getArenaSession().getKillstreak())));
        lines.add(new ScoreboardText(ChatUtils.format("&f&m--------------")));
        lines.add(new ScoreboardText(ChatUtils.format("&bdiscord.nightshadepvp.com")));
        return lines;
    }
}
