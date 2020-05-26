package me.blok601.nightshadeuhc.listener.game;

import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import com.wimbli.WorldBorder.Events.WorldBorderFillCancelEvent;
import com.wimbli.WorldBorder.Events.WorldBorderFillFinishedEvent;
import me.blok601.nightshadeuhc.entity.object.PregenQueue;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.task.PregenTask;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by Blok on 12/13/2018.
 */
public class WorldBorderListener implements Listener {

    private GameManager gameManager;

    public WorldBorderListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onFinish(WorldBorderFillFinishedEvent e) {
        World world = e.getWorld();
        if (PregenTask.isPregenning(world.getName())) {
            PregenQueue queue = PregenTask.getFromWorld(world);
            if (queue == null) {
                Core.get().getLogManager().log(Logger.LogType.DEBUG, "Queue was null!");
                return;
            }
            PregenTask.getPregenQueue().remove(queue);
            queue.setRunning(false);
            PregenTask.RUNNING = false;
            Player player = Bukkit.getPlayer(queue.getStarter());
            if (player != null) {
                player.sendMessage(ChatUtils.message("&eThe pregen for &b" + queue.getWorld().getName() + " &ehas finished!"));
                player.sendMessage(ChatUtils.message("&eGenerating walls for the world now..."));
                gameManager.genWalls(queue.getRadius(), world);
                if (world.getEnvironment() == World.Environment.NORMAL) {
                    if (!gameManager.isOverWorldPregenned()) {
                        gameManager.setOverWorldPregenned(true);
                    }
                }
            }
            Util.staffLog("Pregen for world &b" + queue.getWorld().getName() + " &2has finished!");
            if (world.getEnvironment() == World.Environment.NETHER && world.getName().contains("UHC")) {
                if (gameManager.getWorld() == null) return;
                if (world.getName().startsWith(gameManager.getWorld().getName())) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvnp link nether " + gameManager.getWorld().getName() + " " + world.getName());
                    Util.staffLog("&aWorlds: &e" + gameManager.getWorld().getName() + " &aand &c" + world.getName() + " &ehave been linked!");
                }
            }
        }
    }

    @EventHandler
    public void onCancel(WorldBorderFillCancelEvent e) {
        World world = e.getWorld();
        if (PregenTask.isPregenning(world.getName())) {
            PregenQueue queue = PregenTask.getFromWorld(world);
            if (queue == null) return;
            queue.setRunning(false);
            PregenTask.getPregenQueue().remove(queue);
        }
    }
}
