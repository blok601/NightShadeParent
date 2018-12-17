package me.blok601.nightshadeuhc.command.game.setup;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.gui.setup.HostGUI;
import me.blok601.nightshadeuhc.manager.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 12/13/2018.
 */
public class BetaHostCommand implements UHCCommand {
    @Override
    public String[] getNames() {
        return new String[]{
                "betahost"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        new HostGUI(p, GameManager.get());
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
