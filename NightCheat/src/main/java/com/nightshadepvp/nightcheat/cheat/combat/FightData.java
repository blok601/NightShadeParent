package com.nightshadepvp.nightcheat.cheat.combat;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.entity.Player;

import java.util.*;

public class FightData {

    private long lastDamage;
    private int hits;
    private int swings;
    private boolean swungArm;
    private boolean fighting;

    public FightData() {
        this.lastDamage = -1;
        this.hits = 0;
        this.swings = 0;
        swungArm = false;
        fighting = false;
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

    private static HashMap<UUID, FightData> fightDatas = Maps.newHashMap();

    public static HashMap<UUID, FightData> getFightDatas() {
        return fightDatas;
    }

    public static FightData getFightData(UUID uuid) {
        return getFightDatas().getOrDefault(uuid, new FightData());
    }

    public static FightData getFightData(Player player) {
        return getFightData(player.getUniqueId());
    }

    public static void cleanTask(){
        Collection<Map.Entry<UUID, FightData>> toRemove = Lists.newArrayList();
        for (Map.Entry<UUID, FightData> entry : getFightDatas().entrySet()){
            if(!entry.getValue().isFighting()){

            }
        }
    }
}
