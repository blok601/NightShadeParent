package com.nightshadepvp.tournament.task;

import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.objects.game.SoloMatch;
import com.nightshadepvp.tournament.entity.objects.game.iMatch;
import com.nightshadepvp.tournament.entity.objects.player.PlayerInv;
import com.nightshadepvp.tournament.utils.ActionBarUtil;
import com.nightshadepvp.tournament.utils.InventoryUtils;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Blok on 8/6/2018.
 */
public class LogOutTimerTask extends BukkitRunnable {

    private TPlayer tPlayer;
    private iMatch match;
    private PlayerInv playerInv;
    private boolean running;
    private int counter;

    public LogOutTimerTask(TPlayer tPlayer, iMatch match) {
        this.tPlayer = tPlayer;
        this.match = match;
        running = true;
        counter = 30;
        if(tPlayer != null && tPlayer.isOnline()){
            playerInv = InventoryUtils.playerInventoryFromPlayer(tPlayer.getPlayer());
        }

    }

    @Override
    public void run() {
        if (!running) {
            cancel();
            return;
        }

        if (counter == 0) {
            counter = -1;
            cancel();
            this.running = false;
            if (match instanceof SoloMatch) {
                SoloMatch soloMatch = (SoloMatch) match;
                soloMatch.broadcastAll("&3" + tPlayer.getName() + " &3has been logged out for too long!");
                soloMatch.broadcastAll("&3" + soloMatch.getOpponents(tPlayer).get(0).getName() + " &ewins!");
                soloMatch.endMatch(soloMatch.getOpponents(tPlayer), null);
            }
            return;
        }

        if (counter == 30) {
            if (match instanceof SoloMatch) {
                SoloMatch soloMatch = (SoloMatch) match;
                soloMatch.broadcastAll("&eIf &3" + tPlayer.getName() + " &edoes not log in in &3" + counter + " seconds, they will be disqualified!");
            }
        }

        if (counter == 15) {
            if (match instanceof SoloMatch) {
                SoloMatch soloMatch = (SoloMatch) match;
                soloMatch.broadcastAll("&eIf &3" + tPlayer.getName() + " &edoes not log in in &3" + counter + " seconds, they will be disqualified!");
            }
        }

        if (counter < 5 && counter != 0) {
            if (match instanceof SoloMatch) {
                SoloMatch soloMatch = (SoloMatch) match;
                soloMatch.broadcastAll("&eIf &3" + tPlayer.getName() + " &edoes not log in in &3" + counter + " seconds, they will be disqualified!");
            }
        }

        for (TPlayer opponent : match.getOpponents(tPlayer)) {
            if (opponent.isUsingOldVersion()) {
                continue;
            }

            if (!opponent.isOnline()) continue;

            ActionBarUtil.sendActionBarMessage(opponent.getPlayer(), "§5Combat Timer§8» §3" + this.counter);
        }
        counter--;
    }

    public PlayerInv getPlayerInv() {
        return playerInv;
    }

    public boolean isRunning() {
        return running;
    }

    public int getCounter() {
        return counter;
    }

    public TPlayer getPlayer() {
        return tPlayer;
    }

    public iMatch getMatch() {
        return match;
    }
}
