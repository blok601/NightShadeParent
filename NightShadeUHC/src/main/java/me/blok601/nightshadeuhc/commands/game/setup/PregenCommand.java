package me.blok601.nightshadeuhc.commands.game.setup;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import me.blok601.nightshadeuhc.tasks.pregen.PregenHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 6/23/2018.
 */
public class PregenCommand implements UHCCommand{
    @Override
    public String[] getNames() {
        return new String[]{
                "testgen"

        };

    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        String world = args[0];
        if(Bukkit.getWorld(world) == null){
            return;
        }

        PregenHandler handler = new PregenHandler();
        handler.startPregenTask(Bukkit.getWorld(world), 100, 100, true, (Player) s);
        s.sendMessage(ChatUtils.message("&ePregen started"));
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.OWNER;
    }

    @Override
    public boolean hasRequiredRank() {
        return true;
    }
}
