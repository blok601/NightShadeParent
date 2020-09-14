package com.nightshadepvp.nightcheat.cheat.combat;

import com.google.common.collect.Sets;
import org.bukkit.entity.Player;

import java.util.*;

public class FightData {

    private long lastDamage;
    private int hits;
    private int swings;
    private boolean swungArm;
    private boolean fighting;
    private int fightCooldownTaskID;
    private UUID uuid;

    public FightData(UUID uuid) {
        this.uuid = uuid;
        this.lastDamage = -1;
        this.hits = 0;
        this.swings = 0;
        swungArm = false;
        fighting = false;
        fightCooldownTaskID = -1;
    }

    public long getLastDamage() {
        return lastDamage;
    }

    public void setLastDamage(long lastDamage) {
        this.lastDamage = lastDamage;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getSwings() {
        return swings;
    }

    public void incrementSwings() {
        this.swings += 1;
    }

    public void incrementHits() {
        this.hits += 1;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setSwings(int swings) {
        this.swings = swings;
    }

    public boolean hasSwungArm() {
        return swungArm;
    }

    public void setSwungArm(boolean swungArm) {
        this.swungArm = swungArm;
    }

    public boolean isFighting() {
        return fighting;
    }

    public void setFighting(boolean fighting) {
        this.fighting = fighting;
    }

    public int getFightCooldownTaskID() {
        return fightCooldownTaskID;
    }

    public void setFightCooldownTaskID(int fightCooldownTaskID) {
        this.fightCooldownTaskID = fightCooldownTaskID;
    }

    private static HashSet<FightData> fightDatas = Sets.newHashSet();

    public static HashSet<FightData> getFightDatas() {
        return fightDatas;
    }

    private static FightData getFightData(UUID uuid) {
        return getFightDatas().stream().filter(fightData -> fightData.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    public static FightData getFightData(Player player) {
        if(getFightData(player.getUniqueId()) == null){
            FightData data = new FightData(player.getUniqueId());
            getFightDatas().add(data);
            return data;
        }
        return getFightData(player.getUniqueId());
    }

}
