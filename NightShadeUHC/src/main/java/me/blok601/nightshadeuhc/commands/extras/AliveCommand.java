package me.blok601.nightshadeuhc.commands.extras;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.GameState;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Blok on 9/4/2018.
 */
public class AliveCommand implements CmdInterface {
    @Override
    public String[] getNames() {
        return new String[]{
                "alive"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        StringBuilder stringBuilder = new StringBuilder();
        OfflinePlayer offlinePlayer;
        if(!GameState.gameHasStarted()){
            p.sendMessage(ChatUtils.message("&cThe game hasn't started!"));
            return;
        }
        for (UUID uuid : UHC.players){
            if(Bukkit.getPlayer(uuid) != null){
                //They are online
                stringBuilder.append("&e").append(Bukkit.getPlayer(uuid).getName()).append(", ");
                continue;
            }

            offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            stringBuilder.append("&e").append(offlinePlayer.getName()).append(", ");
        }

        String string = stringBuilder.toString().trim();
        String f = string.substring(0, string.length()-1);
        p.sendMessage(ChatUtils.message("&3Alive Players&8: " + f));
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return null;
    }

    @Override
    public boolean hasRequiredRank() {
        return false;
    }
}
