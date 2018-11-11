package com.nightshadepvp.meetup.task;

import com.nightshadepvp.meetup.Meetup;
import com.nightshadepvp.meetup.Settings;
import com.nightshadepvp.meetup.entity.MPlayerColl;
import com.nightshadepvp.meetup.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Blok on 10/15/2018.
 */
public class PlayerCheckTask extends BukkitRunnable {

    @Override
    public void run() {
        int players = MPlayerColl.get().getAllIngamePlayers().size();

        if (players >= Settings.MIN_PLAYERS) {
            cancel();
            Bukkit.broadcastMessage(ChatUtils.message("&eThe game will begin in &330 &eseconds..."));
            new ScatterTask().runTaskTimer(Meetup.get(), 0, 4L);
            return;
        }else{
            Bukkit.broadcastMessage(ChatUtils.message("&eThe game needs &3" + (Settings.MIN_PLAYERS - players) + " &emore players to start..."));
        }

    }
}
