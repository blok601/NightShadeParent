package me.blok601.nightshadeuhc.task;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayerColl;
import com.wimbli.WorldBorder.Config;
import lombok.Getter;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.object.PregenQueue;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.util.ActionBarUtil;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Blok on 12/14/2018.
 */
public class PregenTask extends BukkitRunnable {

    @Getter
    public static ArrayList<PregenQueue> pregenQueue = new ArrayList<>();

    public static boolean RUNNING = false;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("##.##");

    @Override
    public void run() {

        if(pregenQueue.isEmpty()) return;

        PregenQueue queue = pregenQueue.get(0);
        if (queue == null) {
            return;
        }

        if (queue.isRunning()) {
            NSPlayerColl.get().getAllOnline().stream().filter(nsPlayer -> nsPlayer.hasRank(Rank.TRIAL)).forEach(nsPlayer -> {
                Player player = nsPlayer.getPlayer();
                ActionBarUtil.sendActionBarMessage(player, get(Config.fillTask.getPercentageCompleted(), pregenQueue.get(0).getWorld().getName()), 1, UHC.get());
            });
            return;
        }

        if (RUNNING) {
            NSPlayerColl.get().getAllOnline().stream().filter(nsPlayer -> nsPlayer.hasRank(Rank.TRIAL)).forEach(nsPlayer -> {
                Player player = nsPlayer.getPlayer();
                ActionBarUtil.sendActionBarMessage(player, get(Config.fillTask.getPercentageCompleted(), pregenQueue.get(0).getWorld().getName()), 1, UHC.get());
            });
        }

        Player p = Bukkit.getPlayer(queue.getStarter());
        if (queue.getWorld().getEnvironment() == World.Environment.NETHER) {
            if (p == null) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb " + queue.getWorld().getName() + " set " + GameManager.get().getSetupNetherRadius() + " " + GameManager.get().getSetupNetherRadius() + " 0 0");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb " + queue.getWorld().getName() + " fill 250");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb fill confirm");
                queue.setRunning(true);
                RUNNING = true;
                Util.staffLog("Pregen for world: " + queue.getWorld().getName() + " &ehas begun!");
            } else {
                p.chat("/wb " + queue.getWorld().getName() + " set " + GameManager.get().getSetupNetherRadius() + " " + GameManager.get().getSetupNetherRadius() + " 0 0");
                p.chat("/wb " + queue.getWorld().getName() + " fill 250");
                p.chat("/wb fill confirm");
                queue.setRunning(true);
                RUNNING = true;
                p.sendMessage(ChatUtils.message("&aPregen in world &b" + queue.getWorld().getName() + " &ehas begun!"));
            }
        } else if (queue.getWorld().getEnvironment() == World.Environment.NORMAL) {
            if (p == null) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb " + queue.getWorld().getName() + " set " + GameManager.get().getSetupRadius() + " " + GameManager.get().getSetupRadius() + " 0 0");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb " + queue.getWorld().getName() + " fill 250");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb fill confirm");
                queue.setRunning(true);
                RUNNING = true;
                Util.staffLog("Pregen for world: " + queue.getWorld().getName() + " &ehas begun!");
            } else {
                p.chat("/wb " + queue.getWorld().getName() + " set " + GameManager.get().getSetupRadius() + " " + GameManager.get().getSetupRadius() + " 0 0");
                p.chat("/wb " + queue.getWorld().getName() + " fill 250");
                p.chat("/wb fill confirm");
                queue.setRunning(true);
                RUNNING = true;
                p.sendMessage(ChatUtils.message("&aPregen in world &b" + queue.getWorld().getName() + " &ehas begun!"));
            }
        }
    }

    public static boolean isPregenning(String world) {
        for (PregenQueue queue : pregenQueue) {
            if (queue.getWorld().getName().equalsIgnoreCase(world)) {
                return queue.isRunning();
            }
        }
        return false;
    }

    public static PregenQueue getFromWorld(World world) {
        for (PregenQueue queue : pregenQueue) {
            if (queue.getWorld().getName().equalsIgnoreCase(world.getName())) {
                return queue;
            }
        }

        return null;
    }

    private String get(double pct, String worldName) {
        String coloredPercent;
        if (pct < 25.0) {
            coloredPercent = "§4" + DECIMAL_FORMAT.format(pct) + "%";
        } else if (pct > 25.0 && pct < 50.0) {
            coloredPercent = "§c" + DECIMAL_FORMAT.format(pct) + "%";
        } else if (pct > 50.0 && pct < 75.0) {
            coloredPercent = "§e" + DECIMAL_FORMAT.format(pct) + "%";
        } else if (pct > 75.0 && pct < 100) {
            coloredPercent = "§a" + DECIMAL_FORMAT.format(pct) + "%";
        } else {
            coloredPercent = "§4" + DECIMAL_FORMAT.format(pct) + "%";
        }
        return "§5Pregen in §b" + worldName + "§8» " + coloredPercent;
    }
}
