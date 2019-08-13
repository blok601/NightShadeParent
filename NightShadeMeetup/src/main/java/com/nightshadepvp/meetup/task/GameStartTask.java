package com.nightshadepvp.meetup.task;

import com.google.common.collect.Lists;
import com.nightshadepvp.meetup.entity.MPlayerColl;
import com.nightshadepvp.meetup.entity.handler.GameHandler;
import com.nightshadepvp.meetup.utils.ChatUtils;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Blok on 7/31/2019.
 */
public class GameStartTask extends BukkitRunnable {

    private int counter;
    private GameHandler gameHandler;

    public GameStartTask(GameHandler gameHandler) {
        this.counter = 30;
        this.gameHandler = gameHandler;
    }

    @Override
    public void run() {
        if (counter == 0) {
            this.cancel();
            counter = -10;
            ChatUtils.broadcast("&eThe game is starting...");
            gameHandler.setChatFrozen(true);
            MPlayerColl.get().getAllIngamePlayers().forEach(mPlayer -> {
                if (mPlayer.getPlayer() != null) {
                    mPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 1)); //Make them blind
                    mPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1)); //Make them slow/frozen
                }
            });
            ArrayList<UUID> players = Lists.newArrayList();
            players.addAll(gameHandler.getPlaying());
            new ScatterTask(players, gameHandler);
            return;
        }

        if (counter == 30 || counter == 20 || counter == 15 || counter == 10 || counter < 5) {
            ChatUtils.broadcast("&eThe game will begin in &b" + counter + " &esecond&7(&es&7)");
            return;
        }

    }
}
