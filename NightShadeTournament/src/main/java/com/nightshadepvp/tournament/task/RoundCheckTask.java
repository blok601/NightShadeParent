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
            Bukkit.broadcastMessage(ChatUtils.message("&bRound &f" + RoundHandler.getInstance().getRound() + " &bhas finished!"));
            if(GameHandler.getInstance().getChampionship() != null) { //Have to check if its final game
                Bukkit.broadcastMessage(ChatUtils.message("&bThis Tournament has concluded! Check &f" + Tournament.get().getChallonge().getUrl() + " &b for the final bracket!"));
                return;
            }
            Bukkit.broadcastMessage(ChatUtils.message("&bThe bracket has been updated, check &f" + Tournament.get().getChallonge().getUrl() + " &bfor live updates!"));
            RoundHandler.getInstance().incrementRound();

            GameHandler.getInstance().assignMatches();
        }
    }
}
