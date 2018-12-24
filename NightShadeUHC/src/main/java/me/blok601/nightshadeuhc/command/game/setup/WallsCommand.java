package me.blok601.nightshadeuhc.command.game.setup;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.World;
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
        if (args.length == 0) {
            p.sendMessage(ChatUtils.message("&cUsage: /walls <radius> [world]"));
            return;
        }

        if (args.length == 1) {
            //Just a radius
            int radius = Integer.parseInt(args[0]);
            if (GameManager.get().getWorld() == null) {
                p.sendMessage(ChatUtils.message("&cThe world hasn't been set yet!"));
                return;
            }

            if (!Util.isInt(args[0])) {
                p.sendMessage(ChatUtils.message("&cPlease supply a number!"));
                return;
            }

            GameManager.get().genWalls(radius, GameManager.get().getWorld());
            p.sendMessage(ChatUtils.message("&eThe walls have been set to &3" + radius + " &ex &3" + radius + " &ein world&8: &3" + GameManager.get().getWorld().getName()));
        } else if (args.length == 2) {


            int radius = Integer.parseInt(args[0]);
            World world = Bukkit.getWorld(args[1]);
            if (world == null) {
                p.sendMessage(ChatUtils.message("&cThat world couldn't be found!"));
                return;
            }

            if (!Util.isInt(args[0])) {
                p.sendMessage(ChatUtils.message("&cPlease supply a number!"));
                return;
            }

            GameManager.get().genWalls(radius, world);
            p.sendMessage(ChatUtils.message("&eThe walls have been set to &3" + radius + " &ex &3" + radius + " &ein world&8: &3" + GameManager.get().getWorld().getName()));

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
