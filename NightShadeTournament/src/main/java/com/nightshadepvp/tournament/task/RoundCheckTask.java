package com.nightshadepvp.tournament.task;

import com.nightshadepvp.tournament.Tournament;
import com.nightshadepvp.tournament.entity.handler.GameHandler;
import com.nightshadepvp.tournament.entity.handler.MatchHandler;
import com.nightshadepvp.tournament.entity.handler.RoundHandler;
import com.nightshadepvp.tournament.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class RoundCheckTask extends BukkitRunnable{

    public RoundCheckTask() {
    }

    @Override
    public void run() {
        if(MatchHandler.getInstance().getActiveMatches().size() == 0){
            cancel();
            Bukkit.broadcastMessage(ChatUtils.message("&eRound &3" + RoundHandler.getInstance().getRound() + " &ehas finished!"));
            if(GameHandler.getInstance().getChampionship() != null) { //Have to check if its final game
                Bukkit.broadcastMessage(ChatUtils.message("&eThis Tournament has concluded! Check &b" + Tournament.get().getChallonge().getUrl() + " &e for the final bracket!"));
                return;
            }
            Bukkit.broadcastMessage(ChatUtils.message("&eThe bracket has been updated, check " + Tournament.get().getChallonge().getUrl() + " for live updates!"));
            RoundHandler.getInstance().incrementRound();

            GameHandler.getInstance().assignMatches();
        }
    }
}
