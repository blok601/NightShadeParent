package me.blok601.nightshadeuhc.task;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Blok on 6/12/2019.
 */
public class ShowPlayerTask extends BukkitRunnable {

    private UHC plugin;

    private boolean running;

    public ShowPlayerTask(UHC plugin) {
        this.plugin = plugin;
        this.running = false;
    }

    @Override
    public void run() {
        UHCPlayer uhcPlayer;
        for (Player player : Bukkit.getOnlinePlayers()) {

            for (Player otherPlayers : Bukkit.getOnlinePlayers()) {
                if (!player.canSee(otherPlayers)) {
                    continue;
                }
                uhcPlayer = UHCPlayer.get(otherPlayers);
                if (uhcPlayer.isSpectator()) continue;

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        try {
                            player.showPlayer(otherPlayers);
                        }
                        catch (Exception e) {
                            //do nothing
                        }
                    }
                }.runTaskLater(plugin, 10);

            }

        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
