package me.blok601.nightshadeuhc.command.staff;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 2/19/2018.
 */
public class ToggleHelpopCommand implements UHCCommand{
    @Override
    public String[] getNames() {
        return new String[]{
                "togglehelpop"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        UHCPlayer gamePlayer = UHCPlayer.get(p.getUniqueId());
        if(gamePlayer.isReceiveHelpop()){
            gamePlayer.setReceiveHelpop(false);
            p.sendMessage(ChatUtils.message("&eToggled HelpOP messages&8: &coff"));
            return;
        }

        gamePlayer.setReceiveHelpop(true);
        p.sendMessage(ChatUtils.message("&eToggled HelpOP messages &8: &aon"));
        return;
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
