package me.blok601.nightshadeuhc.tasks;

import com.wimbli.WorldBorder.BorderData;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Blok on 7/18/2017.
 */
public class ShrinkTask extends BukkitRunnable{

    private World world;

    public ShrinkTask(World world) {
        this.world = world;
    }

    @Override
    public void run() {
        new BukkitRunnable() {
            int counter = 10;

            @Override
            public void run() {
                if (counter > 0) {
                    ChatUtils.sendAll("The border will shrink to " + GameManager.getShrinks()[GameManager.getBorderID()] + " radius in " + counter);
                } else if (counter == 0) {
                    BorderData bd = com.wimbli.WorldBorder.WorldBorder.plugin.getWorldBorder(world.getName());
                    bd.setRadius(GameManager.getShrinks()[GameManager.getBorderID()]);
                    GameManager.genWalls(GameManager.getShrinks()[GameManager.getBorderID()]);
                    GameManager.setBorderID(GameManager.getBorderID() + 1);


                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.playSound(player.getLocation(), Sound.BAT_DEATH, 5, 5);
                    }
                    ChatUtils.sendAll("&bThe border has shrunk to " + bd.getRadiusX() + " radius!");


                    if (GameManager.getShrinks()[GameManager.getBorderID() + 1] == 0) {
                        this.cancel();
                        Util.staffLog(ChatUtils.format("&eThe final shrink has occurred"));
                        return;
                    }
                }

                counter--;
            }
        }.runTaskTimer(UHC.get(), 0, 20);
    }
}
