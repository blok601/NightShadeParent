package me.blok601.nightshadeuhc.listeners.game;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.utils.ActionBarUtil;
import me.blok601.nightshadeuhc.events.PvPEnableEvent;
import me.blok601.nightshadeuhc.tasks.WorldBorderTask;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Blok on 2/16/2018.
 */
public class BorderActionBar implements Listener{

    @EventHandler
    public void on(PvPEnableEvent e){

        new BukkitRunnable(){
            @Override
            public void run() {

                if(WorldBorderTask.counter <= 0){
                    this.cancel();
                    return;
                }

                Bukkit.getOnlinePlayers().forEach(player ->{
                    ActionBarUtil.sendActionBarMessage(player, "§5Border shrink in " + get(WorldBorderTask.counter));
                });
            }
        }.runTaskTimer(UHC.get(), 0, 20);


    }

    private String get(int i){
        int m = i/60;
        int s = i%60;

        return "§3" + m + "§5m§3" + s + "§5s";
    }

}
