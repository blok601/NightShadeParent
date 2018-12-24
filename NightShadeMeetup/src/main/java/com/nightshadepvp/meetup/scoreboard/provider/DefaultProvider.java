package com.nightshadepvp.meetup.scoreboard.provider;


import com.nightshadepvp.meetup.scoreboard.ScoreboardProvider;
import com.nightshadepvp.meetup.scoreboard.ScoreboardText;
import com.nightshadepvp.meetup.scoreboard.provider.type.LobbyScoreboard;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class DefaultProvider extends ScoreboardProvider {

    private HashMap<ProviderType, ScoreboardProvider> defaultProvider;

    public DefaultProvider() {
        this.defaultProvider = new HashMap<>();

        this.defaultProvider.put(ProviderType.LOBBY, new LobbyScoreboard());
        //this.defaultProvider.put(ProviderType.INGAME, new IngameScoreboard());
    }

    @Override
    public String getTitle(Player p) {
        return this.defaultProvider.get(ProviderType.LOBBY).getTitle(p);
    }

    @Override
    public List<ScoreboardText> getLines(Player p) {
        return this.defaultProvider.get(ProviderType.LOBBY).getLines(p);
    }

    public enum ProviderType {
        LOBBY, INGAME;
    }

}