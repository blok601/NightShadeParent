package me.blok601.nightshadeuhc.commands.extras;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 2/16/2018.
 */
public class FeedCommand implements CmdInterface{
    @Override
    public String[] getNames() {
        return new String[]{
                "feed"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if(args.length == 0){
            p.setFoodLevel(20);
            p.sendMessage(ChatUtils.message("&eYou have been fed!"));
            return;
        }

        if (args[0].equalsIgnoreCase("*") || args[0].equalsIgnoreCase("all")) {
            Bukkit.getOnlinePlayers().stream().forEach(o -> o.setFoodLevel(20));
            p.sendMessage(ChatUtils.message("&eEveryone has been fed!"));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            p.sendMessage(ChatUtils.message("&cThat player couldn't be found!"));
            return;
        }

        target.setFoodLevel(20);
        p.sendMessage(ChatUtils.message("&eFed: &a" + target.getName()));
        return;
    }

    @Override
    public boolean playerOnly() {
        return false;
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
