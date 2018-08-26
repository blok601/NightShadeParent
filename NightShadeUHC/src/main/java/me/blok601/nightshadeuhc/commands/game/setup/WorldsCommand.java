package me.blok601.nightshadeuhc.commands.game.setup;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

/**
 * Created by Blok on 3/23/2018.
 */
public class WorldsCommand implements CmdInterface{
    @Override
    public String[] getNames() {
        return new String[]{
                "worlds"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        p.sendMessage(ChatUtils.message("&eWorlds List:"));

        Bukkit.getWorlds().stream().filter(world -> world.getName().toLowerCase().contains("uhcworld")).collect(Collectors.toList()).forEach(world -> p.sendMessage(ChatUtils.format("&8 - &e" + world.getName())));

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
