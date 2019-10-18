package com.nightshadepvp.meetup.task;

import com.nightshadepvp.meetup.entity.handler.GameHandler;
import com.nightshadepvp.meetup.entity.object.Map;
import com.nightshadepvp.meetup.utils.ChatUtils;
import com.nightshadepvp.meetup.utils.ScatterUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

/**
 * Created by Blok on 7/31/2019.
 */
public class ScatterTask extends BukkitRunnable {

    private ArrayList<UUID> toScatter;
    private GameHandler gameHandler;
    private Map map;

    public ScatterTask(ArrayList<UUID> toScatter, GameHandler gameHandler) {
        this.toScatter = toScatter;
        Collections.shuffle(toScatter);
        this.gameHandler = gameHandler;
        this.map = gameHandler.getCurrentMap();
    }


    /**
     * Will scatter 5 players a second
     */
    @Override
    public void run() {
        if (toScatter.size() == 0) {
            //Finished
            //TODO: Game start code
        } else {
            Player player = Bukkit.getPlayer(toScatter.get(0));
            if (player == null) {
                gameHandler.getTaskQueue().put(toScatter.get(0), player1 -> {
                    ScatterUtil.scatterPlayer(map.getWorld(), map.getRadius(), player1);
                });
            } else {
                //Online
                ScatterUtil.scatterPlayer(map.getWorld(), map.getRadius(), player);
                player.sendMessage(ChatUtils.message("&eYou have been scattered"));
            }
            toScatter.remove(0);
        }
    }
}
