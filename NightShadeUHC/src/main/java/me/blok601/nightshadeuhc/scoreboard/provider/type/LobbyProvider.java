package me.blok601.nightshadeuhc.scoreboard.provider.type;

import com.google.common.collect.Sets;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.scenario.Scenario;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.scoreboard.ScoreboardProvider;
import me.blok601.nightshadeuhc.scoreboard.ScoreboardText;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Blok on 10/14/2019.
 */
public class LobbyProvider extends ScoreboardProvider {
    private UHC plugin;
    private GameManager gameManager;
    private ScenarioManager scenarioManager;

    public LobbyProvider(UHC uhc, GameManager gameManager, ScenarioManager scenarioManager) {
        this.plugin = uhc;
        this.gameManager = gameManager;
        this.scenarioManager = scenarioManager;
    }

    /**
     * Gets the current Title of the Scoreboard
     *
     * @param p
     * @return the Scoreboard Title
     */
    @Override
    public String getTitle(Player p) {
        return ChatUtils.format("&5NightShade PvP");
    }
    


    /*
    Layout:
    TITLE
    Host
    Team Size:
    SPACE
    Scenarios:
      - Scenario 1
      - Scenario 2
      - Scenario 3
      - X more...
    LINE
    discord
    twitter


     */

    /**
     * Gets the current Sidebar lines of the Scoreboard
     *
     * @param p
     * @return the Scoreboard Sidebar lines
     */
    @Override
    public List<ScoreboardText> getLines(Player p) {
        List<ScoreboardText> lines = new ArrayList<>();
        lines.add(new ScoreboardText(ChatUtils.format("&fHost: &3" + (gameManager.getHost() != null ? gameManager.getHost().getName() : "None"))));
        lines.add(new ScoreboardText(ChatUtils.format("&fTeam Size: &3" + (gameManager.isIsTeam() ? TeamManager.getInstance().getTeamSize() : "FFA"))));
        lines.add(new ScoreboardText(""));
        lines.add(new ScoreboardText(ChatUtils.format("&fScenarios:")));
        for (String s : scenNames()) {
            lines.add(new ScoreboardText(ChatUtils.format("  &f↣ &b" + s)));
        }
        lines.add(new ScoreboardText(ChatUtils.format("&f&m--------------------")));
        lines.add(new ScoreboardText(ChatUtils.format("&bdiscord.nightshadepvp.com")));
        lines.add(new ScoreboardText(ChatUtils.format("&b@NightShadePvPMC")));


        return lines;
    }

    private HashSet<String> scenNames() {
        if (scenarioManager.getEnabledScenarios().size() == 0) return Sets.newHashSet();
        HashSet<String> names = Sets.newHashSet();
        int i = 0;
        if (scenarioManager.getEnabledScenarios().size() <= 3) {
            scenarioManager.getEnabledScenarios().forEach(scenario -> names.add(scenario.getName()));
            return names;
        }

        for (Scenario scen : scenarioManager.getEnabledScenarios()) {
            if (i < 3 && scen.getName().length() < 16) {
                names.add(scen.getName());
                i++;
            } else if (i >= 3) {
                break;
            }
        }

        names.add(ChatUtils.format("" + (scenarioManager.getEnabledScenarios().size() - i) + " more"));
        return names;
    }
}