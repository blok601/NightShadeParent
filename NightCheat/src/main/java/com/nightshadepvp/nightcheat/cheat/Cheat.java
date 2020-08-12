package com.nightshadepvp.nightcheat.cheat;

import com.comphenix.protocol.ProtocolManager;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayerColl;
import com.nightshadepvp.core.utils.ChatUtils;
import com.nightshadepvp.nightcheat.Advantage;
import com.nightshadepvp.nightcheat.ViolationManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class Cheat implements Listener, Advantage {

    private String name;
    private CheatType cheatType;
    private boolean enabled;
    private int violationsToNotify;

    protected CheatManager cheatManager;
    protected ViolationManager violationManager;
    protected ProtocolManager protocolManager;

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

    public CheatManager getCheatManager() {
        return cheatManager;
    }

    public void setCheatManager(CheatManager cheatManager) {
        this.cheatManager = cheatManager;
    }

    public ViolationManager getViolationManager() {
        return violationManager;
    }

    public void setViolationManager(ViolationManager violationManager) {
        this.violationManager = violationManager;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    public void setProtocolManager(ProtocolManager protocolManager) {
        this.protocolManager = protocolManager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CheatType getCheatType() {
        return cheatType;
    }

    public void setCheatType(CheatType cheatType) {
        this.cheatType = cheatType;
    }

    @Override
    public void flag(Player player, Advantage advantage, String message) {
        this.violationManager.getPlayerViolation(player.getUniqueId()).incrementAdvantage(advantage, 1);
        log(player, message);
    }

    @Override
    public void log(Player player, String message) {
        ChatUtils.broadcast("&8[&cNightCheat&8] &c" + player.getName() + " &7failed &c" + this.getName() + " &7(" + message + ") &8[&4" + this.getViolationManager().getPlayerViolation(player.getUniqueId()).getViolations(this) + "&8]", Rank.TRIAL);
        System.out.println("[NightCheat] " + player.getName() + " failed " + this.getName() + "[ " + this.getViolationManager().getPlayerViolation(player.getUniqueId()).getViolations(this) + "]");
    }
}
