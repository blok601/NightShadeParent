package com.nightshadepvp.core.entity;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.TimeUnit;
import com.nightshadepvp.core.ServerType;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;

@EditorName("config")
public class MConf extends Entity<MConf> {

    protected static transient MConf i;
    public static MConf get() { return i; }


    @Override
    public MConf load(MConf that) {
        super.load(that);
        this.setServerName(that.serverName);
        this.setExempt(that.exempt);
        this.setMaintenance(that.maintenance);
        this.setAnnouncerMessages(that.announcer);
        this.setSpawnLocation(that.spawnLocation);
        return this;
    }

    public long cleanInactivityToleranceMillis = 1000 * TimeUnit.MILLIS_PER_DAY; // 60 days

    public long particleDelayTaskMillis = 100L;
    public ServerType serverType = ServerType.PRACTICE;
    private String serverName;
    private ArrayList<String> exempt = new ArrayList<>();
    private boolean maintenance = false;
    private ArrayList<String> announcer = new ArrayList<>();
    private Location spawnLocation = new Location(Bukkit.getWorlds().get(0), 0, 0, 0);


    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
        this.changed();
    }

    public ArrayList<String> getExempt() {
        return exempt;
    }

    public void setExempt(ArrayList<String> exempt) {
        this.exempt = exempt;
        this.changed();
    }

    public ArrayList<String> getAnnouncerMessages() {
        return announcer;
    }

    public void setAnnouncerMessages(ArrayList<String> announcer) {
        this.announcer = announcer;
        this.changed();
    }

    public boolean isMaintenance() {
        return maintenance;
    }

    public void setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }
}
