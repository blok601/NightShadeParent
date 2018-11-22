package com.nightshadepvp.meetup.task;

import com.nightshadepvp.meetup.Meetup;
import com.nightshadepvp.meetup.entity.MPlayerColl;
import com.nightshadepvp.meetup.entity.handler.GameHandler;
import com.nightshadepvp.meetup.utils.ScatterUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * Created by Blok on 10/15/2018.
 */
public class ScatterTask extends BukkitRunnable {

    private ArrayList<Player> players;
    private GameHandler gameHandler;

    public ScatterTask(ArrayList<Player> players, GameHandler gameHandler) {
        this.players = players;
        this.gameHandler = gameHandler;
        MPlayerColl.get().getAllIngamePlayers().forEach(mPlayer -> mPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1, 35)));
    }

    @Override
    public void run() {
        if (players.size() == 0) {
            Bukkit.getOnlinePlayers().forEach(o -> o.removePotionEffect(PotionEffectType.BLINDNESS));
            new GameStartTask().runTaskTimer(Meetup.get(), 0, 20);
            cancel();
            return;
        } else {
            Player p = players.get(0);
            ScatterUtil.scatterPlayer(gameHandler.getMap().getWorld(), gameHandler.getMap().getRadius(), p);
            players.remove(p);
        }
    }
}
