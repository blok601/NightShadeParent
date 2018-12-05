package me.blok601.nightshadeuhc.commands.game.setup;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.utils.ChatUtils;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import me.blok601.nightshadeuhc.manager.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 9/17/2017.
 */
public class SetWorldCommand implements UHCCommand {
    @Override
    public String[] getNames() {
        return new String[]{
                "setworld"
        };
    }

    @Override
    public void onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;

        GameManager.get().setWorld(p.getWorld());

        p.sendMessage(ChatUtils.message("&eYou have set the world to &a" + GameManager.get().getWorld().getName()));

    }

    @Override
    public boolean playerOnly() {
        return false;
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
