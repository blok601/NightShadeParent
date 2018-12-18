package com.nightshadepvp.core.entity;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.TimeUnit;
import com.nightshadepvp.core.ServerType;

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
        return this;
    }

    public long cleanInactivityToleranceMillis = 1000 * TimeUnit.MILLIS_PER_DAY; // 60 days

    public long particleDelayTaskMillis = 100L;
    public ServerType serverType = ServerType.PRACTICE;
    private String serverName;
    private ArrayList<String> exempt = new ArrayList<>();

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
}
