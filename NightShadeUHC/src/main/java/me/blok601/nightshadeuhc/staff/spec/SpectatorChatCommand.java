package me.blok601.nightshadeuhc.staff.spec;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Blok on 7/22/2017.
 */
public class SpectatorChatCommand implements CmdInterface{

    public static ArrayList<UUID> specc = new ArrayList<>();

    @Override
    public String[] getNames() {
        return new String[]{
                "spc"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        UHCPlayer gamePlayer = UHCPlayer.get(p);
        NSPlayer nsPlayer = NSPlayer.get(p);
        if (!gamePlayer.isSpectator() && !nsPlayer.hasRank(Rank.TRIALHOST)) {
            p.sendMessage(ChatUtils.message("&cYou have to be a spectator to join spectator chat!"));
            return;
        }

        if (args.length == 0) {
            //Put them in chat
            if (gamePlayer.isSpectator()) {
                if (specc.contains(p.getUniqueId())) {
                    specc.remove(p.getUniqueId());
                    p.sendMessage(ChatUtils.message("&6Left Spectator chat!"));
                    return;
                } else {
                    specc.add(p.getUniqueId());
                    p.sendMessage(ChatUtils.message("&6Joined Spectator chat!"));
                    return;
                }
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                stringBuilder.append(args[i]).append(" ");
            }

            String f = stringBuilder.toString().trim();
            UHCPlayerColl.get().getSpectators().forEach(uhcPlayer -> uhcPlayer.msg(ChatUtils.format("&8[&3SpecChat&8] &e" + p.getName() + ": &6" + f)));
        }

    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.YOUTUBE;
    }

    @Override
    public boolean hasRequiredRank() {
        return true;
    }
}
