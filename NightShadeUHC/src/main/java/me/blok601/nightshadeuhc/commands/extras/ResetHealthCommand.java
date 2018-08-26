package me.blok601.nightshadeuhc.commands.extras;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 3/20/2018.
 */
public class ResetHealthCommand implements CmdInterface{
    @Override
    public String[] getNames() {
        return new String[]{
                "resethealth"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if(args.length != 1){
            p.sendMessage(ChatUtils.message("&cUsage: /resethealth <player/*>"));
            return;
        }

        if(args[0].equalsIgnoreCase("*")){
            Bukkit.getOnlinePlayers().forEach(o -> o.setMaxHealth(20.0));
            p.sendMessage(ChatUtils.message("&eAll players max health have been set to &620!"));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            p.sendMessage(ChatUtils.message("&cThat player couldn't be found!"));
            return;
        }

        target.setMaxHealth(20.0);
        p.sendMessage(ChatUtils.message("&a" + target.getName() + " 's &emax health has been set to 20!"));
        return;
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.TRIAL;
    }

    @Override
    public boolean hasRequiredRank() {
        return true;
    }
}
