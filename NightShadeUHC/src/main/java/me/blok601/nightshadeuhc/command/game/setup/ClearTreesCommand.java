package me.blok601.nightshadeuhc.command.game.setup;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Blok on 11/27/2018.
 */
public class ClearTreesCommand implements UHCCommand {
    @Override
    public String[] getNames() {
        return new String[]{
                "cleartrees"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;

        World world = GameManager.get().getWorld();
        if (world == null) {
            p.sendMessage(ChatUtils.message("&cThe world has not been set yet!"));
            return;
        }

        p.setGameMode(GameMode.CREATIVE);
        p.setAllowFlight(true);
        p.setFlying(true);
        p.teleport(new Location(world, 0, 80, 0));
        p.chat("//replacenear 100 78,99,100,17,18,161,162 air");
        new BukkitRunnable() {
            @Override
            public void run() {
                p.sendMessage(ChatUtils.message("&eTrees have been cleared around 0, 0!"));
            }
        }.runTaskLater(UHC.get(), 12);

    }

    @Override
    public boolean playerOnly() {
        return true;
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
