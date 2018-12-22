package me.blok601.nightshadeuhc.scenario.cmd;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.scenario.DoubleDatesScenario;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 12/22/2018.
 */
public class DoubleDatesCommand implements UHCCommand {

    private GameManager gameManager;

    public DoubleDatesCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public String[] getNames() {
        return new String[]{
                "doubledates"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;

        if (!ScenarioManager.getScen("Double Dates").isEnabled()) {
            p.sendMessage(ChatUtils.message("&cDouble Dates is not enabled!"));
            return;
        }

        if (TeamManager.getInstance().getTeamSize() != 2) {
            p.sendMessage(ChatUtils.message("&cTeam size must be 2 in Double Dates!"));
            return;
        }

        if (TeamManager.getInstance().getTeams().size() % 2 == 0) {
            p.sendMessage(ChatUtils.message("&cThere must be an even amount of teams! There must be an event amount of teams to start double dates!"));
            p.sendMessage(ChatUtils.message("&cMake sure to kick solos!"));
            return;
        }

        if (args.length != 1) {
            p.sendMessage(ChatUtils.message("&eDouble Dates Help:"));
            p.sendMessage(ChatUtils.format("&b- /doubledates assign - Assigns the new teams"));
            return;
        }

        if (!args[0].equalsIgnoreCase("assign")) {
            p.sendMessage(ChatUtils.message("&eDouble Dates Help:"));
            p.sendMessage(ChatUtils.format("&b- /doubledates assign - Assigns the new teams"));
            return;
        }

        if (!gameManager.getHost().getUniqueId().equals(p.getUniqueId())) {
            p.sendMessage(ChatUtils.message("&cOnly the host can assign double dates!"));
            return;
        }

        DoubleDatesScenario.assignDoubleDates();
        p.sendMessage(ChatUtils.message("&eDouble Dates have been assigned!"));
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.TRIAL;
    }

    @Override
    public boolean hasRequiredRank() {
        return true;
    }
}
