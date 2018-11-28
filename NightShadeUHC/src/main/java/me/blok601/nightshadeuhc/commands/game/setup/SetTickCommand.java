package me.blok601.nightshadeuhc.commands.game.setup;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.Util;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Master on 8/29/2017.
 */
public class SetTickCommand implements UHCCommand{
    @Override
    public String[] getNames() {
        return new String[]{
                "setticks"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if(args.length != 1){
            p.sendMessage(ChatUtils.message("&cUsage: /setticks <name>"));
            return;
        }

        if(!Util.isInt(args[0])){
            p.sendMessage(ChatUtils.message("&cThat is not a number!"));return;
        }

        Util.TICKS = Integer.parseInt(args[0]);

        p.sendMessage(ChatUtils.message("&eUpdated ticks to&8: &a" + Util.TICKS));
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.COOWNER;
    }

    @Override
    public boolean hasRequiredRank() {
        return true;
    }
}
