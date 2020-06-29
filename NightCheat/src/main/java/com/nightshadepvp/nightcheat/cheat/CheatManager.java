package com.nightshadepvp.nightcheat.cheat;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nightshadepvp.nightcheat.NightCheat;
import com.nightshadepvp.nightcheat.Settings;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class CheatManager {

    private NightCheat plugin;
    private HashSet<Cheat> cheats;
    private HashMap<UUID, Integer> violations;

    public CheatManager(NightCheat plugin) {
        this.plugin = plugin;
        cheats = Sets.newHashSet();
        violations = Maps.newHashMap();
        plugin.getServer().getOnlinePlayers().forEach(o -> violations.put(o.getUniqueId(), 0));
        startCleanTask();
    }

    private void startCleanTask() {
        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            this.violations.clear();
            Bukkit.getOnlinePlayers().forEach(o -> this.violations.put(o.getUniqueId(), 0));
        }, 0, Settings.TICKS_TO_CLEAN_CHEATS);
    }

    private void registerCheat(Cheat cheat){
        this.cheats.add(cheat);
    }


}
