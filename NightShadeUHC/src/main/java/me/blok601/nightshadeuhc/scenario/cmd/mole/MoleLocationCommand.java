package me.blok601.nightshadeuhc.scenario.cmd.mole;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.scenario.MolesScenario;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Blok on 7/6/2018.
 */
public class MoleLocationCommand implements UHCCommand{

    private ScenarioManager scenarioManager;

    public MoleLocationCommand(ScenarioManager scenarioManager) {
        this.scenarioManager = scenarioManager;
    }

    @Override
    public String[] getNames() {
        return new String[]{
                "mcl"
        };

    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if(!MolesScenario.moles.containsKey(p.getUniqueId())){
            p.sendMessage(ChatUtils.format(scenarioManager.getScen("Moles").getPrefix() + "&cYou are not a mole!"));
            return;
        }

        String coords = "x - " + Math.ceil(p.getLocation().getX()) + " y - " + Math.ceil(p.getLocation().getY()) + " z- " + Math.ceil(p.getLocation().getZ());

        Player target;
        for (UUID uuid  : MolesScenario.moles.keySet()){
            target = Bukkit.getPlayer(uuid);
            if(target != null) target.sendMessage(ChatUtils.format("&8[&6MoleChat&8] &b" + p.getName() + "&8: &e" + coords));
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
