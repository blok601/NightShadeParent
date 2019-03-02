package me.blok601.nightshadeuhc.command.player;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Blok on 3/2/2019.
 */
public class ShowCommand implements UHCCommand {

    private UHC uhc;

    public ShowCommand(UHC uhc) {
        this.uhc = uhc;
    }

    @Override
    public String[] getNames() {
        return new String[]{
                "show"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;

        if (args.length != 1) {
            p.sendMessage(ChatUtils.message("&cUsage: /show <player>"));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            p.sendMessage(ChatUtils.message("&cThat player couldn't be found!"));
            return;
        }

        if (!p.canSee(target)) {
            p.sendMessage(ChatUtils.message("&cYou are not allowed to see this player!"));
            return;
        }

        if (UHCPlayer.get(target).isSpectator()) {
            p.sendMessage(ChatUtils.message("&cYou are not allowed to see this player!"));
            return;
        }

        p.sendMessage(ChatUtils.message("&eFixing this player's visibility..."));

        Bukkit.getOnlinePlayers().forEach(o -> o.hidePlayer(target));

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(o -> o.showPlayer(target));
            }
        }.runTaskLater(uhc, 10);

        p.sendMessage(ChatUtils.message("&a" + target.getName() + " &eshould now be visible again!"));
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
