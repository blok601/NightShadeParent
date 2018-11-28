package me.blok601.nightshadeuhc.staff.cmd;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Blok on 8/4/2017.
 */
public class CommandSpyCmd implements UHCCommand{

    public static ArrayList<Player> cmdspy = new ArrayList<>();

    @Override
    public String[] getNames() {
        return new String[]{
                "commandspy"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        UHCPlayer uhcPlayer = UHCPlayer.get(p);
        uhcPlayer.setReceivingCommandSpy(!uhcPlayer.isReceivingCommandSpy());

        if(uhcPlayer.isReceivingCommandSpy()){
            p.sendMessage(ChatUtils.message("&eCommandSpy is now toggled &aon"));
            return;
        }

        p.sendMessage(ChatUtils.message("&eCommandSpy is now toggled &coff"));
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
