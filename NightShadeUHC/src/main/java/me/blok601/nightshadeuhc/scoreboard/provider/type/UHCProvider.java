package me.blok601.nightshadeuhc.scoreboard.provider.type;


import me.blok601.nightshadeuhc.GameState;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.scoreboard.ScoreboardProvider;
import me.blok601.nightshadeuhc.scoreboard.ScoreboardText;
import me.blok601.nightshadeuhc.teams.Team;
import me.blok601.nightshadeuhc.teams.TeamManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
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

        lines.add(new ScoreboardText(ChatUtils.format("&5&m--------------------")));
        lines.add(new ScoreboardText(ChatUtils.format("&6Game Clock: &e" + (GameManager.getTimer().isRunning() ? GameManager.getTimer().getTime() : "Waiting..."))));
        lines.add(new ScoreboardText(ChatUtils.format("&6Kills: &e" + GameManager.getKills().getOrDefault(p.getUniqueId(), 0))));
        if (GameManager.isIsTeam()) {
            //Team game
            Team team = TeamManager.getInstance().getTeam(p);


            if (team == null) {
                lines.add(new ScoreboardText(ChatUtils.format("&6Team Kills: &e0")));
            } else {
                //They have a team now
                int teamKills = 0;
                for (String member : team.getMembers()) {
                    if (Bukkit.getPlayer(member) == null) {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(member);
                        if (offlinePlayer == null) continue;
                        teamKills += GameManager.getKills().getOrDefault(offlinePlayer.getUniqueId(), 0);
                    }else{
                        teamKills += GameManager.getKills().getOrDefault(Bukkit.getPlayer(member).getUniqueId(), 0);
                    }
                }
                lines.add(new ScoreboardText(ChatUtils.format("&6Team Kills: &e" + teamKills)));
            }
        }
        if(GameState.gameHasStarted()){
            lines.add(new ScoreboardText(ChatUtils.format("&6Players: &e") + UHC.players.size() + "/" + GameManager.getMaxPlayers()));
        }else{
            lines.add(new ScoreboardText(ChatUtils.format("&6Players: &e" + (Bukkit.getOnlinePlayers().size() - UHCPlayerColl.get().getAllOnline().stream().filter(UHCPlayer::isSpectator).count()))));
        }

        lines.add(new ScoreboardText(ChatUtils.format("&6Spectators: &e" + UHCPlayerColl.get().getAllOnline().stream().filter(UHCPlayer::isSpectator).count())));
        if (GameManager.getWorld() == null) {
            lines.add(new ScoreboardText(ChatUtils.format("&6Border: &eWorld Not Set")));
        } else {
            //Have a world
            lines.add(new ScoreboardText(ChatUtils.format("&6Border: &e" + ((int) GameManager.getBorderSize()))));
        }
        lines.add(new ScoreboardText(ChatUtils.format("&5&m--------------------&r")));
        if(uhcPlayer.isNoClean()){
            lines.add(new ScoreboardText(ChatUtils.format("&6NoClean: &e" + uhcPlayer.getNoCleanTimer() + "s")));
            lines.add(new ScoreboardText(ChatUtils.format("&5&m--------------------&r")));
        }
        lines.add(new ScoreboardText(ChatUtils.format("&ediscord.me/NightShadeMC")));
        return lines;
    }


}