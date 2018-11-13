package me.blok601.nightshadeuhc.staff.cmd;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.NSPlayerColl;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.TimeUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Blok on 11/12/2018.
 */
public class TopSpecCommand implements CmdInterface {

    @Override
    public String[] getNames() {
        return new String[]{
                "topspecs"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        p.sendMessage(ChatUtils.message("&eLoading the top 10 spectator times..."));
        new BukkitRunnable() {
            ArrayList<NSPlayer> nsPlayers = new ArrayList<>();

            @Override
            public void run() {
                for (NSPlayer nsPlayer : NSPlayerColl.get().getAll()) {
                    if (nsPlayer.hasRank(Rank.TRIAL) && nsPlayer.getSpectatingTime() > 1) {
                        nsPlayers.add(nsPlayer);
                    }
                }

                nsPlayers.sort(Comparator.comparing(NSPlayer::getSpectatingTime).reversed());
                ArrayList<NSPlayer> times = new ArrayList<>();
                nsPlayers.stream().limit(10).forEach(times::add);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.sendMessage(ChatUtils.format("&5&m----------------------------"));
                        int i = 1;
                        for (NSPlayer n : times){
                            p.sendMessage(ChatUtils.format("&e" + i +". &3" + n.getName() + " - "  + TimeUtils.formatSecondsToTime(n.getSpectatingTime())));
                            i++;
                        }
                        p.sendMessage(ChatUtils.format("&5&m----------------------------"));
                    }
                }.runTask(UHC.get());
            }
        }.runTaskAsynchronously(UHC.get());
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
