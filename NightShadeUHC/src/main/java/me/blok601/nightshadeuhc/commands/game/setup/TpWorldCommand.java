package me.blok601.nightshadeuhc.commands.game.setup;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 3/23/2018.
 */
public class TpWorldCommand implements UHCCommand{
    @Override
    public String[] getNames() {
        return new String[]{
                "tpworld"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if(args.length != 1){
            p.sendMessage(ChatUtils.message("&cUsage: /tpworld <world>"));
            return;
        }

        World world = Bukkit.getWorld(args[0]);
        if(world == null){
            p.sendMessage(ChatUtils.message("&cThat world couldn't be found!"));
            return;
        }

        p.teleport(world.getSpawnLocation());
        p.sendMessage(ChatUtils.message("&cYou have been teleported to world&8: &6" + world.getName()));


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
