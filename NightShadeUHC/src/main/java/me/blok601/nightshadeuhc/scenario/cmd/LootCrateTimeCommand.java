package me.blok601.nightshadeuhc.scenario.cmd;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import me.blok601.nightshadeuhc.scenario.LootCrateScenario;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Blok on 6/29/2017.
 */
public class LootCrateTimeCommand implements CmdInterface{
    @Override
    public String[] getNames() {
        return new String[]{
                "lct"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
            if(!ScenarioManager.getScen("LootCrates").isEnabled()){
                s.sendMessage(ChatUtils.message("&cLootCrates isn't enabled!"));
                return;
            }

            int i = LootCrateScenario.getTimer()/60;
            s.sendMessage(ChatUtils.message("&eLootcrates will be given out in about &a" + i + " &eminutes."));
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
