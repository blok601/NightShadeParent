package me.blok601.nightshadeuhc.command.staff;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 9/3/2018.
 */
public class SpectatorsCommand implements UHCCommand {
    @Override
    public String[] getNames() {
        return new String[]{
                "specs"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        StringBuilder stringBuilder = new StringBuilder();
        if(UHCPlayerColl.get().getAllOnline().stream().noneMatch(UHCPlayer::isSpectator)){
            p.sendMessage(ChatUtils.message("&cThere are no current spectators!"));
            return;
        }
        for (UHCPlayer uhcPlayer : UHCPlayerColl.get().getAllOnline()){
            if(uhcPlayer.isSpectator()){
                stringBuilder.append("&e").append(uhcPlayer.getName()).append(", ");
            }
        }

        String string = stringBuilder.toString().trim();
        String f = string.substring(0, string.length()-1);
        p.sendMessage(ChatUtils.message("&3Current Spectators&8» " + f));
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
