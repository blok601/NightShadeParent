package me.blok601.nightshadeuhc.tasks;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.stats.handler.StatsHandler;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Blok on 8/9/2018.
 */
public class StatUpdateTask extends BukkitRunnable {

    @Override
    public void run() {
        StatsHandler.getInstance().getWinners().clear();
        StatsHandler.getInstance().getKills().clear();

        ArrayList<UHCPlayer> temp = new ArrayList<>(UHCPlayerColl.get().getAll());

        temp.sort(Comparator.comparing(UHCPlayer::getGamesWon).reversed());
        StatsHandler.getInstance().getWinners().addAll(temp);

        //Sorted all of them

        temp.clear();
        temp = new ArrayList<>(UHCPlayerColl.get().getAll());
        temp.sort(Comparator.comparing(UHCPlayer::getKills).reversed());
        //Sorted all kills
        StatsHandler.getInstance().getKills().addAll(temp);
    }
}
