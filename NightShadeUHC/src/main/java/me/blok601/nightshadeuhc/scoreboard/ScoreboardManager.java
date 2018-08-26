package me.blok601.nightshadeuhc.scoreboard;

import me.blok601.nightshadeuhc.scoreboard.provider.type.UHCProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ScoreboardManager implements Listener {

    private ConcurrentHashMap<Player, PlayerScoreboard> playerScoreboards;

    public ScoreboardManager() {
        this.playerScoreboards = new ConcurrentHashMap<>();

        Bukkit.getOnlinePlayers().forEach(this::addToPlayerCache);
    }

    public boolean hasCachedPlayerScoreboard(Player p) {
        return this.playerScoreboards.containsKey(p);
    }

    public void addToPlayerCache(Player p) {
        if (!this.hasCachedPlayerScoreboard(p))	{
            this.playerScoreboards.put(p, new PlayerScoreboard(new UHCProvider(), p));
        }
    }

    public void updateCache(){
        for (Map.Entry<Player, PlayerScoreboard> scoreboardEntry : this.playerScoreboards.entrySet()){

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