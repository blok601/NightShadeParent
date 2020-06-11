package me.blok601.nightshadeuhc.task;

import com.comphenix.protocol.injector.GamePhase;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.manager.GameManager;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class MobCheckTask extends BukkitRunnable {

    private UHC plugin;
    private GameManager gameManager;
    private boolean stoppedMobs;

    public MobCheckTask(UHC plugin, GameManager gameManager) {
        this.plugin = plugin;
        this.gameManager = gameManager;
        this.stoppedMobs = false;
    }

    @Override
    public void run() {

        if(plugin.getServer().getOnlinePlayers().size() <= 25) return;

        if(plugin.getServer().getOnlinePlayers().size() >= 60){
            if(stoppedMobs) return;

            if(gameManager.getWorld() == null) return;

            World world = gameManager.getWorld();
            world.setGameRuleValue("doMobSpawning", "false");
            this.stoppedMobs = true;
        }else if(plugin.getServer().getOnlinePlayers().size() < 60){
            if(stoppedMobs){
                World world = gameManager.getWorld();
                world.setGameRuleValue("doMobSpawning", "true");
                this.stoppedMobs = false;
            }
        }
    }
}
