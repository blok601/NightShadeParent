package com.nightshadepvp.battlearena;

import com.massivecraft.massivecore.MassivePlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BattleArena extends MassivePlugin {

    private static BattleArena i = new BattleArena();

    public static BattleArena get(){return i;}

    @Override
    public void onEnableInner() {
        this.activateAuto();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
