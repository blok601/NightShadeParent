package com.nightshadepvp.nightshadeproxy;

import net.md_5.bungee.api.plugin.Plugin;

public final class NightShadeProxy extends Plugin {

    @Override
    public void onEnable() {
        getProxy().registerChannel("staffchat");
        getProxy().getPluginManager().registerListener(this, new ServerListener(this));
        getProxy().registerChannel("BungeeCord");

    }

    @Override
    public void onDisable() {
        getProxy().unregisterChannel("staffchat");
        getProxy().unregisterChannel("BungeeCord");
    }
}
