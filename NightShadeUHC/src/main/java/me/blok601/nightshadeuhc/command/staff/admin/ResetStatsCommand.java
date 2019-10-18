package me.blok601.nightshadeuhc.command.staff.admin;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Blok on 1/13/2019.
 */
public class ResetStatsCommand implements UHCCommand {

    private UHC uhc;

    public ResetStatsCommand(UHC uhc) {
        this.uhc = uhc;
    }

    @Override
    public String[] getNames() {
        return new String[]{
                "resetstats"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        p.sendMessage(ChatUtils.message("&eThis is a heavy process, please be patient!"));
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                UHCPlayerColl.get().getAll().forEach(uhcPlayer -> {
                    uhcPlayer.resetStats();
                    uhcPlayer.resetArenaStats();
                    count++;
                });

                p.sendMessage(ChatUtils.message("&eSuccessfully reset &b" + count + " &eplayer's stats!"));
            }
        }.runTaskAsynchronously(uhc);


    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.OWNER;
    }

    @Override
    public boolean hasRequiredRank() {
        return true;
    }
}
