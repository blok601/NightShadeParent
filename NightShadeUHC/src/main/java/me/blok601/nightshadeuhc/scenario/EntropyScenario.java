package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import me.blok601.nightshadeuhc.events.GameStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Blok on 3/20/2018.
 */
public class EntropyScenario extends Scenario {


    public EntropyScenario() {
        super("Entropy", "Every 10 min 1XP level is drained from you. If you don't have any levels when the drain occurs, you die.", new ItemBuilder(Material.EXP_BOTTLE).name("Entropy").make());
    }

    @EventHandler
    public void onGameStart(GameStartEvent e){

        if(!isEnabled()) return;


        Bukkit.getOnlinePlayers().forEach(o -> o.setLevel(1));
        new BukkitRunnable(){
            @Override
            public void run() {
                UHC.players.forEach(uuid -> {
                    Player p = Bukkit.getPlayer(uuid);

                    if(p.getLevel() == 0){
                        p.setHealth(0.0);
                        Bukkit.broadcastMessage(getPrefix() + "&6" + p.getName() + " &4has withered away&8.");
                    }else{
                        p.setLevel(p.getLevel() -1);
                    }


                });
            }
        }.runTaskTimer(UHC.get(), 0, 10*60*20);
    }
}
