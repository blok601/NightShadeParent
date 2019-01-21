package me.blok601.nightshadeuhc.command.staff;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpectatorCommand implements UHCCommand {

    @Override
    public String[] getNames() {
        return new String[]{
                "spec"
        };
    }

    @Override
    public void onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
        Player p = (Player) sender;
        UHCPlayer gamePlayer = UHCPlayer.get(p);
        if (gamePlayer.isSpectator()) {
            //Remove from spec
            gamePlayer.unspec();

            return;
        } else {
            //set spec

            gamePlayer.spec();
            return;
        }
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
