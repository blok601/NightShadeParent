package me.blok601.nightshadeuhc.command.game.setup;

import com.google.common.base.Joiner;
import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.command.UHCCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

/**
 * Created by Blok on 3/23/2018.
 */
public class WorldsCommand implements UHCCommand{
    @Override
    public String[] getNames() {
        return new String[]{
                "worlds"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;

        p.sendMessage(ChatUtils.message("&eWorlds List: &b" + Joiner.on("&7, &b").join(Bukkit.getWorlds().stream().filter(world -> world.getName().toLowerCase().startsWith("UHC")).collect(Collectors.toList()))));

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
