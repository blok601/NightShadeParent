package me.blok601.nightshadeuhc.scoreboard;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.entity.object.PlayerStatus;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.scoreboard.provider.type.ArenaProvider;
import me.blok601.nightshadeuhc.scoreboard.provider.type.LobbyProvider;
import me.blok601.nightshadeuhc.scoreboard.provider.type.UHCProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ScoreboardManager implements Listener {

    private ConcurrentHashMap<Player, PlayerScoreboard> playerScoreboards;
    private UHC plugin;
    private GameManager gameManager;
    private ScenarioManager scenarioManager;

    public ScoreboardManager(UHC uhc, GameManager gameManager, ScenarioManager scenarioManager) {
        this.playerScoreboards = new ConcurrentHashMap<>();
        Bukkit.getOnlinePlayers().forEach(this::addToPlayerCache);

        this.plugin = uhc;
        this.gameManager = gameManager;
        this.scenarioManager = scenarioManager;
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
            if (UHCPlayer.get(scoreboardEntry.getKey()).getPlayerStatus() == PlayerStatus.PLAYING && GameState.gameHasStarted()) {
                scoreboardEntry.setValue(new PlayerScoreboard(new UHCProvider(), scoreboardEntry.getKey()));
            } else if (UHCPlayer.get((scoreboardEntry.getKey())).getPlayerStatus() == PlayerStatus.LOBBY && GameState.gameHasStarted()) {
                scoreboardEntry.setValue(new PlayerScoreboard(new LobbyProvider(plugin, gameManager, scenarioManager), scoreboardEntry.getKey()));
            } else {
                scoreboardEntry.setValue(new PlayerScoreboard(new ArenaProvider(), scoreboardEntry.getKey()));
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