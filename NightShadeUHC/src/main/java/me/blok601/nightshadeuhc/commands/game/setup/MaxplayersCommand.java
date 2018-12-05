package me.blok601.nightshadeuhc.commands.game.setup;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.Util;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 9/24/2017.
 */
public class MaxplayersCommand implements UHCCommand {
    @Override
    public String[] getNames() {
        return new String[]{
                "maxplayers"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if(args.length == 0){
            p.sendMessage(ChatUtils.message("&cUsage: /maxplayers <amount>"));
            return;
        }

        if(!Util.isInt(args[0])){
            p.sendMessage(ChatUtils.message("&cThat is not a number!"));
            return;
        }

        int amt = Integer.parseInt(args[0]);

        if(amt > Bukkit.getMaxPlayers()){
            amt = Bukkit.getMaxPlayers();
        }

        GameManager.get().setMaxPlayers(amt);
        p.sendMessage(ChatUtils.message("&eUpdated max players to &a" + args[0]));
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
