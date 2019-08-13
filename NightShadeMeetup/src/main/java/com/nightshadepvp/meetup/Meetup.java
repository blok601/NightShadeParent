package com.nightshadepvp.meetup;

import com.massivecraft.massivecore.MassivePlugin;
import com.massivecraft.massivecore.store.DriverFlatfile;
import com.massivecraft.massivecore.store.DriverMongo;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import com.nightshadepvp.core.store.NSStore;
import com.nightshadepvp.core.store.NSStoreConf;
import com.nightshadepvp.meetup.entity.handler.GameHandler;

public final class Meetup extends MassivePlugin {

    private GameHandler gameHandler;

    @Override
    public void onEnable() {
        NSStoreConf.get().load();
        NSStore.registerDriver(DriverMongo.get());
        NSStore.registerDriver(DriverFlatfile.get());
        this.activateAuto();

        this.gameHandler = new GameHandler();
        Core.get().getLogManager().log(Logger.LogType.INFO, "NightShadeMeetup v" + this.getDescription().getVersion() + " has been enabled");
    }

    @Override
    public void onDisable() {
        i = null;
    }

    private static Meetup i;

    public Meetup() {
        Meetup.i = this;
    }

    public static Meetup get() {
        return i;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }
}
