package com.nightshadepvp.nightcheat.command;

import com.google.common.collect.Sets;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class DebugCommand implements CommandExecutor {

    public static HashSet<Player> receivingDebug = Sets.newHashSet();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        NSPlayer nsPlayer = NSPlayer.get(sender);
        if(!nsPlayer.hasRank(Rank.ADMIN)){
            nsPlayer.msg(ChatUtils.message("&cNo permission!"));
            return false;
        }

        if(receivingDebug.remove(player)){
            player.sendMessage(ChatUtils.message("&cYou are no longer receiving debug messages!"));
            return false;
        }

        receivingDebug.add(player);
        player.sendMessage(ChatUtils.message("&aYou are now receiving debug messages"));
        return false;
    }
}
