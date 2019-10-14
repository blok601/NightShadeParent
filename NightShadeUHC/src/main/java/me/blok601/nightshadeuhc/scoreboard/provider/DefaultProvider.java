package me.blok601.nightshadeuhc.scoreboard.provider;


import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.scoreboard.ScoreboardProvider;
import me.blok601.nightshadeuhc.scoreboard.ScoreboardText;
import me.blok601.nightshadeuhc.scoreboard.provider.type.ArenaProvider;
import me.blok601.nightshadeuhc.scoreboard.provider.type.LobbyProvider;
import me.blok601.nightshadeuhc.scoreboard.provider.type.UHCProvider;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class DefaultProvider extends ScoreboardProvider {

    private HashMap<ProviderType, ScoreboardProvider> defaultProvider;
    private UHC plugin;
    private GameManager gameManager;
    private ScenarioManager scenarioManager;


    public DefaultProvider(UHC uhc, GameManager gameManager, ScenarioManager scenarioManager) {
        this.plugin = uhc;
        this.gameManager = gameManager;
        this.scenarioManager = scenarioManager;

        this.defaultProvider = new HashMap<>();

        this.defaultProvider.put(ProviderType.UHC, new UHCProvider());
        this.defaultProvider.put(ProviderType.ARENA, new ArenaProvider());
        this.defaultProvider.put(ProviderType.LOBBY, new LobbyProvider(plugin, gameManager, scenarioManager));
    }

    @Override
    public String getTitle(Player p) {
        return this.defaultProvider.get(ProviderType.UHC).getTitle(p);
    }

    @Override
    public List<ScoreboardText> getLines(Player p) {
        return this.defaultProvider.get(ProviderType.UHC).getLines(p);
    }

    public enum ProviderType {
        UHC, ARENA, LOBBY
    }

}