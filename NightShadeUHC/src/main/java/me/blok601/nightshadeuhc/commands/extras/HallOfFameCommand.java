package me.blok601.nightshadeuhc.commands.extras;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import me.blok601.nightshadeuhc.stats.handler.StatsHandler;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.PagedInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 11/13/2018.
 */
public class HallOfFameCommand implements CmdInterface {

    @Override
    public String[] getNames() {
        return new String[]{
                "halloffame"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        p.sendMessage(ChatUtils.message("&eLoading Hall of Fame Inventory..."));
        new PagedInventory(StatsHandler.getInstance().getHallOfFameInventory(), ChatUtils.format("&6Hall of Fame"), p);
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return null;
    }

    @Override
    public boolean hasRequiredRank() {
        return false;
    }
}
