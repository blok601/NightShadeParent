package me.blok601.nightshadeuhc.commands.extras;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.GameState;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 7/9/2018.
 */
public class ArenaCommand implements UHCCommand {
    @Override
    public String[] getNames() {
        return new String[]{
                "arena"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        UHCPlayer uhcPlayer = UHCPlayer.get(p);
        if (GameState.getState() != GameState.WAITING) {
            p.sendMessage(ChatUtils.message("&cYou can't enter the arena right now!"));
            return;
        }

        if (uhcPlayer.isInArena()) {
            uhcPlayer.leaveArena();
            p.sendMessage(ChatUtils.message("&eYou have left the arena!"));
        } else {
            uhcPlayer.joinArena();
            p.sendMessage(ChatUtils.message("&eYou have joined the arena!"));
        }

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
