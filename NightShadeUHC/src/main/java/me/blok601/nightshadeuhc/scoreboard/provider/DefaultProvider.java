package me.blok601.nightshadeuhc.scoreboard.provider;


import me.blok601.nightshadeuhc.scoreboard.ScoreboardProvider;
import me.blok601.nightshadeuhc.scoreboard.ScoreboardText;
import me.blok601.nightshadeuhc.scoreboard.provider.type.UHCProvider;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class DefaultProvider extends ScoreboardProvider {

    private HashMap<ProviderType, ScoreboardProvider> defaultProvider;

    public DefaultProvider() {
        this.defaultProvider = new HashMap<>();

        this.defaultProvider.put(ProviderType.UHC, new UHCProvider());
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
        UHC;
    }

}