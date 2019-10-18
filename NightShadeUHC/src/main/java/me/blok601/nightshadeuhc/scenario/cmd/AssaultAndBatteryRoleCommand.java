package me.blok601.nightshadeuhc.scenario.cmd;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.entity.object.Team;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 8/4/2018.
 */
public class AssaultAndBatteryRoleCommand implements UHCCommand {

    private ScenarioManager scenarioManager;

    public AssaultAndBatteryRoleCommand(ScenarioManager scenarioManager) {
        this.scenarioManager = scenarioManager;
    }

    @Override
    public String[] getNames() {
        return new String[]{
                "abrole"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;

        if (!GameState.gameHasStarted()) {
            p.sendMessage(ChatUtils.message("&cThe game hasn't started!"));
            return;
        }

        if (!scenarioManager.getScen("Assault and Battery").isEnabled()) {
            p.sendMessage(ChatUtils.message("&cAssault and Battery is not enabled!"));
            return;
        }

        Team team = TeamManager.getInstance().getTeam(p);
        if (team == null) {
            p.sendMessage(ChatUtils.message("&eYou can do both ranged and melee attacks since you are a solo!"));
            return;
        }

        if (team.getMembers().size() == 1) {
            p.sendMessage(ChatUtils.message("&eYou can do both ranged and melee attacks since your teammates have died!"));
            return;
        }

        if (team.getBow().equals(p.getUniqueId())) {
            p.sendMessage(ChatUtils.message("&eYou can only do &3projectile attacks"));
            return;
        }

        if (team.getMelee().equals(p.getUniqueId())) {
            p.sendMessage(ChatUtils.message("&eYou can only do &3melee attacks"));
            return;
        }
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public Rank getRequiredRank() {
        return null;
    }

    @Override
    public boolean hasRequiredRank() {
        return false;
    }
}
