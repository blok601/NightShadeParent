package me.blok601.nightshadeuhc.tasks;

import lombok.Getter;
import me.blok601.nightshadeuhc.entity.object.PregenQueue;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * Created by Blok on 12/14/2018.
 */
public class PregenTask extends BukkitRunnable {

    @Getter
    public static ArrayList<PregenQueue> pregenQueue = new ArrayList<>();

    public static boolean RUNNING = false;

    @Override
    public void run() {

        if(pregenQueue.isEmpty()) return;

        PregenQueue queue = pregenQueue.get(0);
        if (queue == null) {
            return;
        }

        if (queue.isRunning()) {
            return;
        }

        if(RUNNING) return;

        Player p = Bukkit.getPlayer(queue.getStarter());
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
}
