package com.nightshadepvp.nightcheat;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import com.nightshadepvp.nightcheat.cheat.CheatManager;
import com.nightshadepvp.nightcheat.exploit.ExploitManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class NightCheat extends JavaPlugin {

    private ProtocolManager protocolManager;
    private CheatManager cheatManager;
    private ExploitManager exploitManager;
    private ViolationManager violationManager;

    @Override
    public void onEnable() {
        long now = System.currentTimeMillis();
        Core.get().getLogManager().log(Logger.LogType.INFO, "[NightCheat] Initializing beginning...");
        this.violationManager = new ViolationManager(this);
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        this.cheatManager = new CheatManager(this);
        this.exploitManager = new ExploitManager(this);
        this.getServer().getPluginManager().registerEvents(violationManager, this);
        long totalTimeInMillis = System.currentTimeMillis() - now;
        long seconds = totalTimeInMillis / 1000;
        long millis = totalTimeInMillis % 1000;
        Core.get().getLogManager().log(Logger.LogType.INFO, "[NightCheat] Enable finished in (" + seconds + "." + millis + ")");
        Core.get().getLogManager().log(Logger.LogType.INFO, "[NightCheat] NightCheat v" + this.getDescription().getVersion() + " Enabled!");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public CheatManager getCheatManager() {
        return cheatManager;
    }

    public ViolationManager getViolationManager() {
        return violationManager;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    public ExploitManager getExploitManager() {
        return exploitManager;
    }
}
