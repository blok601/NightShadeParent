package com.nightshadepvp.meetup.scoreboard;

import com.nightshadepvp.meetup.Meetup;
import com.nightshadepvp.meetup.scoreboard.provider.type.LobbyScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ScoreboardManager implements Listener {

    private ConcurrentHashMap<Player, PlayerScoreboard> playerScoreboards;
    private Meetup plugin;

    public ScoreboardManager(Meetup plugin) {
        this.playerScoreboards = new ConcurrentHashMap<>();
        this.plugin = plugin;

        Bukkit.getOnlinePlayers().forEach(this::addToPlayerCache);
    }

    public boolean hasCachedPlayerScoreboard(Player p) {
        return this.playerScoreboards.containsKey(p);
    }

    public void addToPlayerCache(Player p) {
        if (!this.hasCachedPlayerScoreboard(p))	{
            this.playerScoreboards.put(p, new PlayerScoreboard(new LobbyScoreboard(plugin.getGameHandler()), p));
        }
    }

    public void updateCache(){
        for (Map.Entry<Player, PlayerScoreboard> scoreboardEntry : this.playerScoreboards.entrySet()){
            if(this.plugin.getGameHandler().inGame()){
                //scoreboardEntry.setValue(new PlayerScoreboard(new IngameScoreboard(), p));
            }else{
                scoreboardEntry.setValue(new PlayerScoreboard(new LobbyScoreboard(plugin.getGameHandler()), scoreboardEntry.getKey()));
            }
        }
    }

    public void removeFromPlayerCache(Player p) {
        if (this.hasCachedPlayerScoreboard(p)) {
            this.playerScoreboards.remove(p).disappear();
        }
    }

    public PlayerScoreboard getPlayerScoreboard(Player p) {
        if (!this.hasCachedPlayerScoreboard(p)) this.addToPlayerCache(p);

        return this.playerScoreboards.get(p);
    }

    public ConcurrentHashMap<Player, PlayerScoreboard> getPlayerScoreboards() {
        return this.playerScoreboards;
    }

}