package me.blok601.nightshadeuhc.listeners.game;

import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import com.wimbli.WorldBorder.Events.WorldBorderFillFinishedEvent;
import me.blok601.nightshadeuhc.entity.object.PregenQueue;
import me.blok601.nightshadeuhc.tasks.PregenTask;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by Blok on 12/13/2018.
 */
public class WorldBorderListener implements Listener {

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
            }
            Util.staffLog("Pregen for world &b" + queue.getWorld().getName() + " &2has finished!");
        }
    }

}
