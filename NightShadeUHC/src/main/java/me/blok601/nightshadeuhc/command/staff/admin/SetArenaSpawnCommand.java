package me.blok601.nightshadeuhc.command.staff.admin;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.MConf;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 7/9/2018.
 */
public class SetArenaSpawnCommand implements UHCCommand {
    @Override
    public String[] getNames() {
        return new String[]{
                "setarenaspawn"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        MConf.get().updateArenaLocation(p.getLocation());
        p.sendMessage(ChatUtils.message("&eYou have set the arena location!"));
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.ADMIN;
    }

    @Override
    public boolean hasRequiredRank() {
        return true;
    }
}
