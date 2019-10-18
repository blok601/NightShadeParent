package me.blok601.nightshadeuhc.command.player;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.stat.handler.StatsHandler;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.PagedInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Blok on 11/13/2018.
 */
public class HallOfFameCommand implements UHCCommand {

    @Override
    public String[] getNames() {
        return new String[]{
                "halloffame"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if (StatsHandler.getInstance().getHallOfFameInventory().isEmpty()) {
            p.sendMessage(ChatUtils.message("&cThe hall of fame has not been loaded yet!"));
            return;
        }
        p.sendMessage(ChatUtils.message("&eLoading Hall of Fame Inventory..."));
        new PagedInventory(StatsHandler.getInstance().getHallOfFameInventory(), ChatUtils.format("&6Hall of Fame"), p);
        new BukkitRunnable(){
            @Override
            public void run() {
                p.updateInventory();
            }
        }.runTaskLater(UHC.get(), 1);
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
