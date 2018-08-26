package me.blok601.nightshadeuhc.server.cmd;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.PlayerUtils;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 8/4/2017.
 */
public class PlayerLocationCommand implements CmdInterface{

    @Override
    public String[] getNames() {
        return new String[]{
                "locreport"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        p.sendMessage(ChatUtils.format("&5+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+="));
        p.sendMessage(ChatUtils.format("&3Player Location Count:"));
        for (String string : PlayerUtils.locations.keySet()){
            p.sendMessage(ChatUtils.format("&e" + string + ": " + PlayerUtils.locations.get(string) + " player(s)"));
        }
        p.sendMessage(ChatUtils.format("&5+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+="));
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
