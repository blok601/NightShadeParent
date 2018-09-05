package me.blok601.nightshadeuhc.commands.extras;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Blok on 9/4/2018.
 */
public class ClearXPCommand implements CmdInterface {
    @Override
    public String[] getNames() {
        return new String[]{
                "clearxp"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        UHCPlayer uhcPlayer = UHCPlayer.get(s);
        for (UHCPlayer uhcPlayer1 : UHCPlayerColl.get().getAllOnline()){
            uhcPlayer1.getPlayer().setExp(0F);
            uhcPlayer1.getPlayer().setLevel(0);
        }

        uhcPlayer.msg(ChatUtils.message("&eXP has been &acleared!"));
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.TRIALHOST;
    }

    @Override
    public boolean hasRequiredRank() {
        return true;
    }
}
