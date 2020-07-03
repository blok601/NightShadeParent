package com.nightshadepvp.nightcheat.cheat;

import com.google.common.collect.Sets;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import com.nightshadepvp.nightcheat.NightCheat;

import java.util.HashSet;

public class CheatManager {

    private NightCheat plugin;
    private HashSet<Cheat> cheats;

    public CheatManager(NightCheat plugin) {
        this.plugin = plugin;
        cheats = Sets.newHashSet();
    }

    private void registerCheat(Cheat cheat){
        this.cheats.add(cheat);
        plugin.getServer().getPluginManager().registerEvents(cheat, plugin);
        cheat.setCheatManager(this);
        cheat.setProtocolManager(plugin.getProtocolManager());
        cheat.setViolationManager(plugin.getViolationManager());
        Core.get().getLogManager().log(Logger.LogType.INFO, "[NightCheat] Registered " + cheat.getName() + ".");
    }


}
