package me.blok601.nightshadeuhc.commands.extras;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 3/22/2018.
 */
public class ClearArmorCommand implements CmdInterface{
    @Override
    public String[] getNames() {
        return new String[]{
                "cleararmor"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;


        if(args.length != 1){
            p.sendMessage(ChatUtils.message("&cUsage: /cleararmor <player/*>"));
            return;
        }

        if(args[0].equalsIgnoreCase("*")){
            Bukkit.getOnlinePlayers().forEach(o -> o.getInventory().setArmorContents(null));
            p.sendMessage(ChatUtils.message("&eSuccessfully cleared everyone's armor!"));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            p.sendMessage(ChatUtils.message("&cThat player couldn't be found!"));
            return;
        }

        target.getInventory().setArmorContents(null);
        p.sendMessage(ChatUtils.message("&eYo have cleared " + target.getName() + "'s armor!"));
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
