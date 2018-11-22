package me.blok601.nightshadeuhc.scenario.cmd.mole;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.GameState;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import me.blok601.nightshadeuhc.gui.MoleKitGUI;
import me.blok601.nightshadeuhc.scenario.MolesScenario;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 7/6/2018.
 */
public class MoleKitCommand implements CmdInterface{
    @Override
    public String[] getNames() {
        return new String[]{
                "molekit"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if(!ScenarioManager.getScen("Moles").isEnabled()){
            p.sendMessage(ChatUtils.message("&cMoles isn't enabled!"));
            return;
        }

        if(!GameState.gameHasStarted()){
            p.sendMessage(ChatUtils.message("&cThe game hasn't started yet!"));
            return;
        }

        if(MolesScenario.moles == null || !MolesScenario.moles.containsKey(p.getUniqueId())){
            p.sendMessage(ChatUtils.message("&cYou are not a mole!"));
            return;
        }

        if(MolesScenario.moles.get(p.getUniqueId())){
            p.sendMessage(ChatUtils.format(ScenarioManager.getScen("Moles").getPrefix() + "&cYou have already redeemed your mole kit!"));
            return;
        }

        p.sendMessage(ChatUtils.format(ScenarioManager.getScen("Moles").getPrefix() + "&eOpening mole kits..."));
        new MoleKitGUI(p);
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
