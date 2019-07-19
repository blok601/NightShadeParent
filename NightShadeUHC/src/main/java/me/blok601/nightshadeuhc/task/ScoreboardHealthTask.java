package me.blok601.nightshadeuhc.task;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.scoreboard.PlayerScoreboard;
import me.blok601.nightshadeuhc.scoreboard.ScoreboardManager;
import me.blok601.nightshadeuhc.util.MathUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Map;

/**
 * Created by Blok on 8/19/2018.
 */
public class ScoreboardHealthTask extends BukkitRunnable {

    private ScoreboardManager scoreboardManager;

    public ScoreboardHealthTask(ScoreboardManager scoreboardManager) {
        this.scoreboardManager = scoreboardManager;
    }

    @Override
    public void run() {

        Scoreboard scoreboard;
        Objective tab;
        Objective belowNameHealthObjective;

        for (Map.Entry<Player, PlayerScoreboard> playerPlayerScoreboardEntry : scoreboardManager.getPlayerScoreboards().entrySet()) {

            if (playerPlayerScoreboardEntry.getValue() == null) continue;

            scoreboard = playerPlayerScoreboardEntry.getValue().getBukkitScoreboard();

            if (scoreboard.getObjective("tabHealth") == null) {
                scoreboard.registerNewObjective("tabHealth", "dummy");
            }

            tab = scoreboard.getObjective("tabHealth");
            tab.setDisplaySlot(DisplaySlot.PLAYER_LIST);


            for (UHCPlayer uhcPlayer : UHCPlayerColl.get().getAllPlaying()) {
                double health = MathUtil.getPercentage(uhcPlayer.getPlayer().getHealth(), 20.0);
                tab.getScore(uhcPlayer.getName()).setScore((int) health);
            }


            // -----------------------
            // Below Name Health
            // ----------------------

            scoreboard = playerPlayerScoreboardEntry.getValue().getBukkitScoreboard();

            if (scoreboard.getObjective("health") != null) continue; //Don't worry about it, already registered

            belowNameHealthObjective = scoreboard.registerNewObjective("health", "health");
            belowNameHealthObjective.setDisplayName(ChatColor.RED + "❤");
            belowNameHealthObjective.setDisplaySlot(DisplaySlot.BELOW_NAME); //Done and registered for every player
        }
    }
}
