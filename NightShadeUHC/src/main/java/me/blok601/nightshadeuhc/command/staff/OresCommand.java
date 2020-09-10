package me.blok601.nightshadeuhc.command.staff;

import com.nightshadepvp.core.Rank;
import com.sun.xml.internal.ws.api.server.WSEndpoint;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.gui.leaderboards.OreLeaderboard;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OresCommand implements UHCCommand {

    @Override
    public String[] getNames() {
        return new String[]{
                "ores"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player player = (Player) s;
        player.sendMessage(ChatUtils.message("&bOpening the ore leaderboard for this game.9.."));
        new OreLeaderboard(player);
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
