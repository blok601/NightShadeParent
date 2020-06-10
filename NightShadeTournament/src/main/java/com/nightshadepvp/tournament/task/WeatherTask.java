package com.nightshadepvp.tournament.task;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class WeatherTask extends BukkitRunnable {

    private JavaPlugin plugin;

    public WeatherTask(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach(world -> world.setTime(1000));
    }
}
