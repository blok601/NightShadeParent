package me.blok601.nightshadeuhc.listener.game;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.component.ComponentHandler;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.event.GameEndEvent;
import me.blok601.nightshadeuhc.event.GameStartEvent;
import me.blok601.nightshadeuhc.event.MeetupStartEvent;
import me.blok601.nightshadeuhc.event.PvPEnableEvent;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.stat.CachedGame;
import me.blok601.nightshadeuhc.stat.handler.StatsHandler;
import me.blok601.nightshadeuhc.task.WorldBorderTask;
import me.blok601.nightshadeuhc.util.ActionBarUtil;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ScatterUtil;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Blok on 11/11/2017.
 */
public class GameListener implements Listener {

    private GameManager gameManager;

    public GameListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onStart(GameStartEvent e){
        //if(UHC.players.size() >= 15){//Only count if game has 15 player fill or more
            StatsHandler.getInstance().getCachedGame().setStart(System.currentTimeMillis());
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

            cachedGame.setEnd(System.currentTimeMillis());
            cachedGame.setHost(GameManager.get().getHost().getUniqueId().toString());
            ArrayList<String> winners = new ArrayList<>();
            NSPlayer targetNSPlayer;
            for (UUID winner : e.getWinners()){
                winners.add(winner.toString());
                targetNSPlayer = NSPlayer.get(winner); //This is just for now
                if (targetNSPlayer.getRank().getValue() < Rank.DRAGON.getValue()) {
                    //If they are less than Dragon
                    targetNSPlayer.setRank(Rank.DRAGON);
                }
            }
            cachedGame.setWinners(winners);
            ScenarioManager.getEnabledScenarios().forEach(scenario -> scenarios.add(scenario.getName()));
            cachedGame.setScenarios(scenarios);

            for (UUID winner : e.getWinners()) {
                winnerKills.put(winner.toString(), GameManager.get().getKills().getOrDefault(winner, 0));
            }
            cachedGame.setWinnerKills(winnerKills);

            if (GameManager.get().isIsTeam()) {
                if (TeamManager.getInstance().isRandomTeams()) {
                    cachedGame.setTeamType("Random To" + TeamManager.getInstance().getTeamSize());
                } else {
                    //Custom
                    cachedGame.setTeamType("cTo" + TeamManager.getInstance().getTeamSize());
                }
            } else {
                cachedGame.setTeamType("FFA");
            }

            cachedGame.setServer(GameManager.get().getServerType());

            Document document = new Document("matchID", cachedGame.getMatchID());
            document.append("host", cachedGame.getHost());
            document.append("winners", cachedGame.getWinners());
            document.append("scenarios", cachedGame.getScenarios());
            document.append("players", cachedGame.getFill());
            document.append("winnerKills", cachedGame.getWinnerKills());
            document.append("teamType", cachedGame.getTeamType());
            document.append("startTime", cachedGame.getStart());
            document.append("endTime", cachedGame.getEnd());
            document.append("server", GameManager.get().getServerType());

            UHC.get().getGameCollection().insertOne(document);
        });
    }

    @EventHandler
    public void on(PvPEnableEvent e){

        new BukkitRunnable(){
            @Override
            public void run() {

                if(WorldBorderTask.counter <= 0){
                    this.cancel();
                    return;
                }

                Bukkit.getOnlinePlayers().forEach(player ->{
                    ActionBarUtil.sendActionBarMessage(player, "§5Border shrink in " + get(WorldBorderTask.counter));
                });
            }
        }.runTaskTimer(UHC.get(), 0, 20);


    }

    @EventHandler
    public void onMeetup(MeetupStartEvent e) {
        for (UHCPlayer uhcPlayer : UHCPlayerColl.get().getAllPlaying()) {
            if (uhcPlayer.getPlayer().getWorld().getEnvironment() == World.Environment.NETHER) {
                ScatterUtil.scatterPlayer(gameManager.getWorld(), gameManager.getShrinks()[gameManager.getBorderID()], uhcPlayer.getPlayer());
                uhcPlayer.msg(ChatUtils.message("&eYou have been scattered out of the nether!"));
            }
        }
        ComponentHandler.getInstance().getComponent("Nether").setEnabled(false);
    }

    private String get(int i){
        int m = i/60;
        int s = i%60;

        return "§3" + m + "§5m§3" + s + "§5s";
    }

}
