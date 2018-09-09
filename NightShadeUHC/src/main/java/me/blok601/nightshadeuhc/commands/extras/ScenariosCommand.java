package me.blok601.nightshadeuhc.commands.extras;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.fanciful.FancyMessage;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import me.blok601.nightshadeuhc.scenario.Scenario;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Blok on 3/7/2018.
 */
public class ScenariosCommand implements CmdInterface{
    @Override
    public String[] getNames() {
        return new String[]{
                "scenarios"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        ArrayList<Scenario> scenarios = ScenarioManager.getEnabledScenarios();
        StringBuilder builder = new StringBuilder();
        FancyMessage fancyMessage = new FancyMessage();
        for (Scenario scenario : scenarios){
            //builder.append("&3").append(scenario.getName()).append("&8,");
            fancyMessage.text(ChatUtils.format("&eEnabled Scenarios&8» ")).then(scenario.getName() + ",").color(ChatColor.DARK_AQUA).tooltip(scenario.getDesc());
        }

        //p.sendMessage(ChatUtils.format("&eEnabled Scenarios&8» " + builder.toString().substring(0, builder.toString().length())));
        fancyMessage.send(p);
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
