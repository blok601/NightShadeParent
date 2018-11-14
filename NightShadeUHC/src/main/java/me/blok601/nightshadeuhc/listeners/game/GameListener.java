package me.blok601.nightshadeuhc.listeners.game;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.events.GameEndEvent;
import me.blok601.nightshadeuhc.events.GameStartEvent;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.stats.CachedGame;
import me.blok601.nightshadeuhc.stats.handler.StatsHandler;
import me.blok601.nightshadeuhc.teams.TeamManager;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Blok on 11/11/2017.
 */
public class GameListener implements Listener {

    @EventHandler
    public void onStart(GameStartEvent e){
        //if(UHC.players.size() >= 15){//Only count if game has 15 player fill or more
            StatsHandler.getInstance().getCachedGame().setStart(new Timestamp(System.currentTimeMillis()));
            StatsHandler.getInstance().getCachedGame().setFill(UHC.players.size());

            Bukkit.getServer().getScheduler().runTaskAsynchronously(UHC.get(), () -> StatsHandler.getInstance().getCachedGame().setMatchID(UHC.get().getGameCollection().count() + 1));
        //}
    }

    @EventHandler
    public void onEnd(GameEndEvent e) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(UHC.get(), () -> {
            HashMap<String, Integer> winnerKills = new HashMap<>();
            CachedGame cachedGame = StatsHandler.getInstance().getCachedGame();
            ArrayList<String> scenarios = new ArrayList<>();

            cachedGame.setEnd(new Timestamp(System.currentTimeMillis()));
            cachedGame.setHost(GameManager.getHost().getUniqueId().toString());
            ArrayList<String> winners = new ArrayList<>();
            for (UUID winner : e.getWinners()){
                winners.add(winner.toString());
            }
            cachedGame.setWinners(winners);
            ScenarioManager.getEnabledScenarios().forEach(scenario -> scenarios.add(scenario.getName()));
            cachedGame.setScenarios(scenarios);

            for (UUID winner : e.getWinners()) {
                winnerKills.put(winner.toString(), GameManager.getKills().getOrDefault(winner, 0));
            }
            cachedGame.setWinnerKills(winnerKills);

            if (GameManager.isIsTeam()) {
                if (TeamManager.getInstance().isRandomTeams()) {
                    cachedGame.setTeamType("Random To" + TeamManager.getInstance().getTeamSize());
                } else {
                    //Custom
                    cachedGame.setTeamType("cTo" + TeamManager.getInstance().getTeamSize());
                }
            } else {
                cachedGame.setTeamType("FFA");
            }

            cachedGame.setServer(GameManager.getServerType());

            Document document = new Document("matchID", cachedGame.getMatchID());
            document.append("host", cachedGame.getHost());
            document.append("winners", cachedGame.getWinners());
            document.append("scenarios", cachedGame.getScenarios());
            document.append("players", cachedGame.getFill());
            document.append("winnerKills", cachedGame.getWinnerKills());
            document.append("teamType", cachedGame.getTeamType());
            document.append("startTime", cachedGame.getStart());
            document.append("endTime", cachedGame.getEnd());
            document.append("server", GameManager.getServerType());

            UHC.get().getGameCollection().insertOne(document);
        });
    }

}
