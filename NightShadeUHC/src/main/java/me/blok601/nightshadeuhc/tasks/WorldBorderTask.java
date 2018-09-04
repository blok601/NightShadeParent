package me.blok601.nightshadeuhc.tasks;

import com.wimbli.WorldBorder.BorderData;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.GameState;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.utils.ActionBarUtil;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.Util;
import me.blok601.nightshadeuhc.events.MeetupStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Consumer;

/**
 * Created by Blok on 3/17/2017.
 */
public class WorldBorderTask extends BukkitRunnable {

    public static int counter;
    private World world;
    private int first;

    public WorldBorderTask(int counter, World world, int first) {
        this.counter = counter;
        this.world = world;
        this.first = first;
    }

    @Override
    public void run() {
        BorderData bd = com.wimbli.WorldBorder.WorldBorder.plugin.getWorldBorder(world.getName());

        if (counter > 0) {
            if (counter == (counter - 300)) {
                //5 mins before
                Util.staffLog("The border will shrink in 5 minutes!");
                ChatUtils.sendAll("The border will shrink to " + GameManager.getFirstShrink() + " radius in 5 minutes");
                Bukkit.getOnlinePlayers().forEach((Consumer<Player>) player -> {
                    ActionBarUtil.sendActionBarMessage(player, "§5Shrink to " + GameManager.getFirstShrink() + "radius in " + get(counter), 1, UHC.get());
                });
            }else if(Util.isBetween(10, 0, counter)){
                ChatUtils.sendAll("The border will shrink to " + GameManager.getFirstShrink() + " radius in " + counter);
            }
        } else if (counter == 0) {
            GameState.setState(GameState.MEETUP);
            bd.setRadius(first);
            GameManager.genWalls(first);
            GameManager.setBorderID(GameManager.getBorderID()+1);
            for (Player pls : Bukkit.getOnlinePlayers()) {
                pls.playSound(pls.getLocation(), Sound.BAT_DEATH, 5, 1);
            }
            ChatUtils.sendAll("&bThe border has shrunk to " + GameManager.getFirstShrink() + " radius!");
            Bukkit.getServer().getPluginManager().callEvent(new MeetupStartEvent());
            counter = -1;
            new ShrinkTask(world).runTaskTimer(UHC.get(), 300 * Util.TICKS, 300 * Util.TICKS);
            this.cancel();
            return;
        }
        counter--;
    }


    private String get(int i) {
        int m = i / 60;
        int s = i % 60;

        return "§3" + m + "§5m§3" + s + "§5s";
    }
}
