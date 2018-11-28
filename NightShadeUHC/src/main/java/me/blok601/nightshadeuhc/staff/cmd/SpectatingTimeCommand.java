package me.blok601.nightshadeuhc.staff.cmd;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 11/11/2018.
 */
public class SpectatingTimeCommand implements UHCCommand {
    @Override
    public String[] getNames() {
        return new String[]{
                "spectime"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if (args.length == 0) {
            //Send their own time
            NSPlayer nsPlayer = NSPlayer.get(p);
            p.sendMessage(ChatUtils.message("&eYou have spectated for " + TimeUtils.formatSecondsToTime(nsPlayer.getSpectatingTime())));
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                if (offlinePlayer == null) {
                    p.sendMessage(ChatUtils.message("&cThat player couldn't be found!"));
                    return;
                }

                NSPlayer targetNSPlayer = NSPlayer.get(offlinePlayer.getUniqueId());
                if (targetNSPlayer == null) {
                    p.sendMessage(ChatUtils.message("&cThat player has never joined the server!"));
                    return;
                }

                p.sendMessage(ChatUtils.message("&e" + targetNSPlayer.getName() + " has spectated for " + TimeUtils.formatSecondsToTime(targetNSPlayer.getSpectatingTime())));
            } else {
                NSPlayer targetNSPlayer = NSPlayer.get(target);
                if (targetNSPlayer == null) {
                    p.sendMessage(ChatUtils.message("&cThere was a problem loading that player's data!"));
                    return;
                }

                p.sendMessage(ChatUtils.message("&e" + targetNSPlayer.getName() + " has spectated for " + TimeUtils.formatSecondsToTime(targetNSPlayer.getSpectatingTime())));
            }
        } else {
            p.sendMessage(ChatUtils.message("&cUsage: /spectime [player]"));
            return;
        }
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
