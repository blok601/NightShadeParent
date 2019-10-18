package me.blok601.nightshadeuhc.stat.command;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.gui.leaderboards.StatsGUI;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 7/4/2018.
 */
public class StatsCommand implements UHCCommand {
    @Override
    public String[] getNames() {
        return new String[]{
                "stats"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if (args.length > 1) {
            p.sendMessage(ChatUtils.message("&cUsage: /stats <player>"));
            return;
        }

        String targetName;
        if (args.length == 0) {
            targetName = p.getName();
        } else {
            targetName = args[0];
        }
        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            //Offline route
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(targetName);
            if (offlinePlayer == null) {
                p.sendMessage(ChatUtils.message("&cThat player couldn't be found!"));
                return;
            }

            if (!offlinePlayer.hasPlayedBefore()) {
                p.sendMessage(ChatUtils.message("&cThat player has never joined the server!"));
                return;
            }

            UHCPlayer targetUHCPlayer = UHCPlayer.get(offlinePlayer.getUniqueId());
            if (targetUHCPlayer == null) {
                p.sendMessage(ChatUtils.message("&cThere was a problem loading that player! Please try again later..."));
                return;
            }

            new StatsGUI(targetUHCPlayer, p);
            return;
        }

        UHCPlayer uhcPlayer = UHCPlayer.get(target);

        if (uhcPlayer == null || !uhcPlayer.hasPlayedBefore()) {
            p.sendMessage(ChatUtils.message("&cThat player has never joined the server!"));
            return;
        }
        new StatsGUI(uhcPlayer, p);
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
