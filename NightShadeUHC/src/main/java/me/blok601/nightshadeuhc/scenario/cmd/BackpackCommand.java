package me.blok601.nightshadeuhc.scenario.cmd;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import me.blok601.nightshadeuhc.scenario.BackpackScenario;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Master on 7/14/2017.
 */
public class BackpackCommand implements UHCCommand{

    @Override
    public String[] getNames() {
        return new String[]{
                "backpack"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        NSPlayer user= NSPlayer.get(p.getUniqueId());

        if(!ScenarioManager.getScen("BackPack").isEnabled()){
            s.sendMessage(ChatUtils.message("&cBackpacks isn't enabled!"));
            return;
        }


        if(args.length == 0){
            if(!BackpackScenario.bps.containsKey(p.getUniqueId())){
                p.sendMessage(ChatUtils.message("&cYou don't have a backpack...creating one for you."));
                BackpackScenario.bps.put(p.getUniqueId(), Bukkit.createInventory(null, 27, p.getName() + "'s BackPack"));
                p.openInventory(BackpackScenario.bps.get(p.getUniqueId()));
            }else{
                p.sendMessage(ChatUtils.message("&6Opening backpack..."));
                p.openInventory(BackpackScenario.bps.get(p.getUniqueId()));
            }
        }else if(args.length == 1){
            if(user.hasRank(Rank.TRIAL)){
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null){
                    p.sendMessage(ChatUtils.message("&cThat player couldn't be found"));
                    return;
                }
                if(BackpackScenario.bps.containsKey(target.getUniqueId())){
                    p.sendMessage(ChatUtils.message("&eOpening &a" + target.getName() + "&e's backpack..."));
                    p.openInventory(BackpackScenario.bps.get(target.getUniqueId()));
                }else{
                    p.sendMessage(ChatUtils.message("&cThat player doesn't have a backpack"));
                }
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
