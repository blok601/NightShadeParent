package me.blok601.nightshadeuhc.scenario.cmd.mole;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import me.blok601.nightshadeuhc.scenario.MolesScenario;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Blok on 7/6/2018.
 */
public class MoleChatCommand implements CmdInterface{
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
            p.sendMessage(ChatUtils.format(ScenarioManager.getScen("Moles").getPrefix() + "&cYou are not a mole!"));
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
                if(target != null) target.sendMessage(ChatUtils.format("&8[&6MoleChat&8] &b" + p.getName() + "&8: &e" + msg));
            }
        }else{
            p.sendMessage(ChatUtils.message("&cUsage: /mmc <message>"));
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
