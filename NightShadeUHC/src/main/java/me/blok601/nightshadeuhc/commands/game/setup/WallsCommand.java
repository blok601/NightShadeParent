package me.blok601.nightshadeuhc.commands.game.setup;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 7/16/2018.
 */
public class WallsCommand implements UHCCommand{
    @Override
    public String[] getNames() {
        return new String[]{
                "walls"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if (args.length != 1) {
            p.sendMessage(ChatUtils.message("&cUsage: /walls <radius>"));
            return;
        }

        int radius = Integer.parseInt(args[0]);
        if(GameManager.get().getWorld() == null){
            p.sendMessage(ChatUtils.message("&cThe world hasn't been set yet!"));
            return;
        }

        if(!Util.isInt(args[0])){
            p.sendMessage(ChatUtils.message("&cPlease supplya a number!"));
            return;
        }

        GameManager.get().genWalls(radius);
        p.sendMessage(ChatUtils.message("&eThe walls have been set to &3" + radius + " &ex &3" + radius + " &ein world&8: &3" + GameManager.get().getWorld().getName()));
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
