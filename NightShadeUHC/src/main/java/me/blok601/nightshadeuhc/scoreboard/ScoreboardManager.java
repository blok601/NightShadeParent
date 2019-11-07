package me.blok601.nightshadeuhc.scoreboard;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.command.player.teams.TeamBaseCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.object.CachedColor;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.entity.object.PlayerStatus;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.scoreboard.provider.type.ArenaProvider;
import me.blok601.nightshadeuhc.scoreboard.provider.type.LobbyProvider;
import me.blok601.nightshadeuhc.scoreboard.provider.type.UHCProvider;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Scoreboard;

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
            } else if (UHCPlayer.get((scoreboardEntry.getKey())).getPlayerStatus() == PlayerStatus.LOBBY && !GameState.gameHasStarted()) {
                scoreboardEntry.setValue(new PlayerScoreboard(new LobbyProvider(plugin, gameManager, scenarioManager), scoreboardEntry.getKey()));
            } else if (GameState.gameHasStarted()) {
                scoreboardEntry.setValue(new PlayerScoreboard(new UHCProvider(), scoreboardEntry.getKey()));
            } else if (UHCPlayer.get(scoreboardEntry.getKey()).getPlayerStatus() == PlayerStatus.ARENA) {
                scoreboardEntry.setValue(new PlayerScoreboard(new ArenaProvider(), scoreboardEntry.getKey()));
            } else {
                scoreboardEntry.setValue(new PlayerScoreboard(new UHCProvider(), scoreboardEntry.getKey()));
            }
            if (!TeamBaseCommand.colors.containsKey(scoreboardEntry.getKey().getUniqueId())) continue;
            colorFix(scoreboardEntry.getKey(), TeamBaseCommand.colors.get(scoreboardEntry.getKey().getUniqueId()));




        }
    }

    public void colorFix(Player player, String c) {
        String color = ChatUtils.generateTeamColor();
        String playerString = player.getName().length() >= 13 ? player.getName().substring(0, 11) : player.getName();
        String name = "UHC" + playerString;


        for (Map.Entry<Player, PlayerScoreboard> playerPlayerScoreboardEntry : getPlayerScoreboards().entrySet()){
            if(playerPlayerScoreboardEntry.getValue() == null) continue;
            if(playerPlayerScoreboardEntry.getKey() == null) continue;

            Scoreboard scoreboard = playerPlayerScoreboardEntry.getValue().getBukkitScoreboard();
            if(scoreboard.getTeam(name) != null) {
                scoreboard.getTeam(name).unregister();
            }

            org.bukkit.scoreboard.Team t = scoreboard.registerNewTeam(name);
            t.setPrefix(ChatUtils.format(color));
            t.addEntry(player.getName());
            CachedColor cachedColor = new CachedColor(name);
            cachedColor.setColor(color);
            cachedColor.setPlayer(player.getName());
            TeamManager.getInstance().getCachedColors().add(cachedColor);
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
