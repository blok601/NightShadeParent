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
public class MoleChatCommand implements UHCCommand{


    private ScenarioManager scenarioManager;

    public MoleChatCommand(ScenarioManager scenarioManager) {
        this.scenarioManager = scenarioManager;
    }

    @Override
    public String[] getNames() {
        return new String[]{
                "mcc"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if(!MolesScenario.moles.containsKey(p.getUniqueId())){
            p.sendMessage(ChatUtils.format(scenarioManager.getScen("Moles").getPrefix() + "&cYou are not a mole!"));
            return;

        }

        if(args.length > 0){
            //They put a msg
            StringBuilder stringBuilder = new StringBuilder();
            for (int i =0; i < args.length; i++){
                stringBuilder.append(args[i]).append(" ");
            }

            String msg = stringBuilder.toString().trim();

            Player target;
            for (UUID uuid  : MolesScenario.moles.keySet()){
                target = Bukkit.getPlayer(uuid);
                if(target != null) target.sendMessage(ChatUtils.format("&8[&6Moles&8] &b" + p.getName() + "&8: &e" + msg));
            }
        }else{
            p.sendMessage(ChatUtils.message("&cUsage: /mcc <message>"));
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
