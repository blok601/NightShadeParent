package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.event.ScenarioEnableEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by Blok on 3/20/2018.
 */
public class SkriptScenarios implements Listener {

    @EventHandler
    public void onEnable(ScenarioEnableEvent e){
        Scenario scenario = e.getScenario();

        if(scenario != null && scenario.getCommands() != null && !scenario.getCommands().isEmpty()){
            scenario.getCommands().forEach(s -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s));
        }
    }

}
