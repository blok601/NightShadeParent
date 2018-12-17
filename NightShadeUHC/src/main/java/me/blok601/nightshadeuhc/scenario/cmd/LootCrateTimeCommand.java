package me.blok601.nightshadeuhc.scenario.cmd;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.scenario.LootCrateScenario;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Blok on 6/29/2017.
 */
public class LootCrateTimeCommand implements UHCCommand{
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
            s.sendMessage(ChatUtils.message("&eLootcrates will be given out in about &a" + i + " &eminute&8(s&8)&e."));
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
