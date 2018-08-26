package me.blok601.nightshadeuhc.scenario.cmd;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import me.blok601.nightshadeuhc.scenario.Scenario;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 2/10/2018.
 */
public class ExplainCommand implements CmdInterface{
    @Override
    public String[] getNames() {
        return new String[]{
                "explain"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if(args.length == 0){
            p.sendMessage(ChatUtils.message("&cUsage: /explain <scenario>"));
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++){
            builder.append(args[i]).append(" ");
        }

        String scen = builder.toString().trim();
        Scenario scenario = ScenarioManager.getScen(scen);
        if(scenario == null){
            p.sendMessage(ChatUtils.message("&cThat scenario couldn't be found!"));
            return;
        }

        p.sendMessage(ChatUtils.format("&4" + scenario.getName() + "&8» &6" + scenario.getDesc()));
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
