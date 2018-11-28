package me.blok601.nightshadeuhc.scenario.cmd;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.GameState;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.scenario.TeamInventoryScenario;
import me.blok601.nightshadeuhc.teams.Team;
import me.blok601.nightshadeuhc.teams.TeamManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 9/28/2018.
 */
public class TeamInventoryCommand implements UHCCommand {
    @Override
    public String[] getNames() {
        return new String[]{
                "teaminventory"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if (!GameState.gameHasStarted()) {
            p.sendMessage(ChatUtils.message("&cThe game has not started yet!"));
            return;
        }
        Team team = TeamManager.getInstance().getTeam(p);
        if (team == null) {
            //They are a solo
            if(TeamInventoryScenario.soloInventories.containsKey(p.getUniqueId())){
                // They already got an inventory in there
                p.openInventory(TeamInventoryScenario.soloInventories.get(p.getUniqueId()));
                p.sendMessage(ChatUtils.format(ScenarioManager.getScen("Team Inventory").getPrefix() + "&eOpening your team inventory..."));
                return;
            }

            //Create and open
            TeamInventoryScenario.soloInventories.put(p.getUniqueId(), Bukkit.createInventory(null, 27, "Team Inventory"));
            p.openInventory(TeamInventoryScenario.soloInventories.get(p.getUniqueId()));
            p.sendMessage(ChatUtils.format(ScenarioManager.getScen("Team Inventory").getPrefix() + "&eOpening your team inventory..."));
        }else{
            //Team
            if(TeamInventoryScenario.teamInventories.containsKey(team)){
                //Have a inventory
                p.openInventory(TeamInventoryScenario.teamInventories.get(team));
                p.sendMessage(ChatUtils.format(ScenarioManager.getScen("Team Inventory").getPrefix() + "&eOpening your team inventory..."));
            }else{
                TeamInventoryScenario.teamInventories.put(team, Bukkit.createInventory(null, 27, "Team Inventory"));
                p.openInventory(TeamInventoryScenario.teamInventories.get(team));
                p.sendMessage(ChatUtils.format(ScenarioManager.getScen("Team Inventory").getPrefix() + "&eOpening your team inventory..."));
            }
        }

    }

    @Override
    public boolean playerOnly() {
        return true;
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
