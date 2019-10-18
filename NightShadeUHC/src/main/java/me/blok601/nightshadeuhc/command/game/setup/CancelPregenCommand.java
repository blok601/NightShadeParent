package me.blok601.nightshadeuhc.command.game.setup;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 7/16/2018.
 */
public class CancelPregenCommand implements UHCCommand{
    @Override
    public String[] getNames() {
        return new String[]{
                "cancelpregen"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if(!CreateWorldCommand.pendingGen.contains(p.getUniqueId())){
            p.sendMessage(ChatUtils.message("&cYou haven't started a pregen!"));
            return;
        }

        CreateWorldCommand.pendingGen.remove(p.getUniqueId());
        p.sendMessage(ChatUtils.message("&eYou have cancelled the pregen!"));
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
