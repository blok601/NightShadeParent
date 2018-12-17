package me.blok601.nightshadeuhc.scoreboard.provider.type;


import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.scoreboard.ScoreboardProvider;
import me.blok601.nightshadeuhc.scoreboard.ScoreboardText;
import me.blok601.nightshadeuhc.entity.object.Team;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class UHCProvider extends ScoreboardProvider {

    @Override
    public String getTitle(Player p) {
        return ChatUtils.format("&5NightShadePvP");
    }

    /**
     * You can use up to 15 Scoreboard lines.
     * Max-length of your text is 32.
     */
    @Override
    public List<ScoreboardText> getLines(Player p) {
        UHCPlayer uhcPlayer = UHCPlayer.get(p);
        List<ScoreboardText> lines = new ArrayList<>();

        lines.add(new ScoreboardText(ChatUtils.format("&f&m--------------------")));
        lines.add(new ScoreboardText(ChatUtils.format("&fGame Clock: &b" + (GameManager.get().getTimer().isRunning() ? GameManager.get().getTimer().getTime() : "Waiting..."))));
        lines.add(new ScoreboardText(ChatUtils.format("&fKills: &b" + GameManager.get().getKills().getOrDefault(p.getUniqueId(), 0))));
        if (GameManager.get().isIsTeam()) {
            //Team game
            Team team = TeamManager.getInstance().getTeam(p);


            if (team == null) {
                lines.add(new ScoreboardText(ChatUtils.format("&fTeam Kills: &b0")));
            } else {
                //They have a team now
                int teamKills = 0;
                for (String member : team.getMembers()) {
                    if (Bukkit.getPlayer(member) == null) {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(member);
                        if (offlinePlayer == null) continue;
                        teamKills += GameManager.get().getKills().getOrDefault(offlinePlayer.getUniqueId(), 0);
                    }else{
                        teamKills += GameManager.get().getKills().getOrDefault(Bukkit.getPlayer(member).getUniqueId(), 0);
                    }
                }
                lines.add(new ScoreboardText(ChatUtils.format("&fTeam Kills: &b" + teamKills)));
            }
        }
        if(GameState.gameHasStarted()){
            lines.add(new ScoreboardText(ChatUtils.format("&fPlayers: &b") + UHC.players.size() + "/" + GameManager.get().getMaxPlayers()));
        }else{
            lines.add(new ScoreboardText(ChatUtils.format("&fPlayers: &b" + (Bukkit.getOnlinePlayers().size() - UHCPlayerColl.get().getAllOnline().stream().filter(UHCPlayer::isSpectator).count()))));
        }

        lines.add(new ScoreboardText(ChatUtils.format("&fSpectators: &b" + UHCPlayerColl.get().getAllOnline().stream().filter(UHCPlayer::isSpectator).count())));
        if (GameManager.get().getWorld() == null) {
            lines.add(new ScoreboardText(ChatUtils.format("&fBorder: &bWorld Not Set")));
        } else {
            //Have a world
            lines.add(new ScoreboardText(ChatUtils.format("&fBorder: &b" + ((int) GameManager.get().getBorderSize()))));
        }
        lines.add(new ScoreboardText(ChatUtils.format("&f&m--------------------&r")));
        if(uhcPlayer.isNoClean()){
            lines.add(new ScoreboardText(ChatUtils.format("&fNoClean: &b" + uhcPlayer.getNoCleanTimer() + "s")));
            lines.add(new ScoreboardText(ChatUtils.format("&f&m--------------------&r")));
        }
        lines.add(new ScoreboardText(ChatUtils.format("&bdiscord.me/NightShadeMC")));
        return lines;
    }


}