package me.blok601.nightshadeuhc.command.game.setup;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.MConf;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.Util;
import me.blok601.nightshadeuhc.command.UHCCommand;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 3/23/2018.
 */
public class DeleteWorldCommand implements UHCCommand{
    @Override
    public String[] getNames() {
        return new String[]{
                "deleteworld"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if(args.length != 1){
            p.sendMessage(ChatUtils.message("&cUsage: /deleteworld <name>"));
            return;
        }

        if(MConf.get().getSpawnLocation().getWorld().equalsIgnoreCase(args[0])){
            p.sendMessage(ChatUtils.message("&cYou can't delete the spawn world!"));
            return;
        }

        if(MConf.get().getArenaLocation().getWorld().equalsIgnoreCase(args[0])){
            p.sendMessage(ChatUtils.message("&cYou can't delete the arena world!"));
            return;
        }

        World world = Bukkit.getWorld(args[0]);
        if(world == null){
            p.sendMessage(ChatUtils.message("&cThat world couldn't be found!"));
            return;
        }

        world.getPlayers().forEach(player -> player.teleport(MConf.get().getSpawnLocation().asBukkitLocation(true)));

        UHC.getMultiverseCore().getMVWorldManager().removeWorldFromConfig(args[0]);
        Bukkit.unloadWorld(world.getName(), false);
        Util.deleteWorldFolder(world);
        p.sendMessage(ChatUtils.message("&eSuccessfully deleted world &6" + world.getName()));
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.HOST;
    }

    @Override
    public boolean hasRequiredRank() {
        return true;
    }
}
