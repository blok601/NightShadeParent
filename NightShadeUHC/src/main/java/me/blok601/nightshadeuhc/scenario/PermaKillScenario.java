package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import me.blok601.nightshadeuhc.events.CustomDeathEvent;
import me.blok601.nightshadeuhc.events.GameStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;

/**
 * Created by Blok on 1/28/2018.
 */
public class PermaKillScenario extends Scenario{


    public PermaKillScenario() {
        super("PermaKill", "Every time someone is killed, it swaps from day to night or night to day", new ItemBuilder(Material.DIAMOND_SWORD).name("PermaKill").make());
    }

    boolean day;

    @EventHandler
    public void onDeath(CustomDeathEvent e){
        if(!isEnabled()){
            return;
        }

        if(day){
            day = false;
            GameManager.getWorld().setTime(14000);
            Bukkit.broadcastMessage(ChatUtils.format(getPrefix() + " &eIt is now night!"));
        }else{
            day = true;
            GameManager.getWorld().setTime(6000);
            Bukkit.broadcastMessage(ChatUtils.format(getPrefix() + " &eIt is now day!"));

        }


    }

    @EventHandler
    public void onStart(GameStartEvent e){
        if(!isEnabled()){
            return;
        }


        GameManager.getWorld().setTime(6000);
        GameManager.getWorld().setGameRuleValue("doDaylightCycle", "false");
        GameManager.getWorld().setGameRuleValue("doWeatherCycle", "false");
        day = true;
        Bukkit.broadcastMessage(ChatUtils.format(getPrefix() + " &ePermaKill has started!"));
    }
}
