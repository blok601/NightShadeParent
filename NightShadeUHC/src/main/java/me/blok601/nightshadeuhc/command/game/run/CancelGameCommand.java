package me.blok601.nightshadeuhc.command.game.run;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.command.UHCCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 7/20/2017.
 */
public class CancelGameCommand implements UHCCommand {

    @Override
    public String[] getNames() {
        return new String[]{
                "cancelgame"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if(StartUHCCommand.arrayList.contains(p.getName())){
            StartUHCCommand.arrayList.remove(p.getName());
            p.sendMessage(ChatUtils.message("&bYour game has been cancelled!"));
            return;
        }else{
            p.sendMessage(ChatUtils.message("&cYou don't have a game to cancel!"));
            return;
        }
    }

    @Override
    public boolean playerOnly() {
        return true;
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
