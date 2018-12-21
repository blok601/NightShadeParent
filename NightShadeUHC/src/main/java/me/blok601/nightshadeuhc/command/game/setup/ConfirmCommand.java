package me.blok601.nightshadeuhc.command.game.setup;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 12/20/2018.
 */
public class ConfirmCommand implements UHCCommand {
    @Override
    public String[] getNames() {
        return new String[]{
                "confirm"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if (!PlayerUtils.getToConfirm().containsKey(p.getUniqueId())) {
            p.sendMessage(ChatUtils.message("&cYou have nothing to confirm!"));
            return;
        }

        PlayerUtils.getToConfirm().get(p.getUniqueId()).run();
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
