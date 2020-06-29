package com.nightshadepvp.nightcheat.cheat;

import org.bukkit.event.Listener;

public abstract class Cheat implements Listener {

    private String name;
    private CheatType cheatType;
    private boolean enabled;
    private int violationsToNotify;

    public Cheat(String name, CheatType cheatType) {
        this.name = name;
        this.cheatType = cheatType;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getViolationsToNotify() {
        return violationsToNotify;
    }

    public void setViolationsToNotify(int violationsToNotify) {
        this.violationsToNotify = violationsToNotify;
    }
}
