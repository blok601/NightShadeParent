package me.blok601.nightshadeuhc.command.staff.admin;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearStatsCommand implements UHCCommand {
    @Override
    public String[] getNames() {
        return new String[]{
                "transferstats"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;

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
