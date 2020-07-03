package com.nightshadepvp.nightcheat;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class ViolationManager {

    private NightCheat plugin;
    private HashSet<PlayerViolation> violations;

    public ViolationManager(NightCheat plugin) {
        this.plugin = plugin;
        this.violations = Sets.newHashSet();
        startCleanTask();
    }

    public PlayerViolation getPlayerViolation(UUID uuid){
        return this.violations.stream().filter(playerViolation -> playerViolation.getUuid().equals(uuid)).findFirst().orElse(null);
    }


    private void startCleanTask() {
        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            this.violations.clear();
            Bukkit.getOnlinePlayers().forEach(o -> this.violations.add(new PlayerViolation(o.getUniqueId(), Maps.newHashMap())));
        }, 0, Settings.TICKS_TO_CLEAN_CHEATS);
    }

    public HashSet<PlayerViolation> getViolations() {
        return violations;
    }

    public static class PlayerViolation {

        private UUID uuid;
        private HashMap<Advantage, Integer> violationMap;

        public PlayerViolation(UUID uuid, HashMap<Advantage, Integer> violationMap) {
            this.uuid = uuid;
            this.violationMap = violationMap;
        }

        public UUID getUuid() {
            return uuid;
        }

        public void setUuid(UUID uuid) {
            this.uuid = uuid;
        }

        public HashMap<Advantage, Integer> getViolationMap() {
            return violationMap;
        }

        public int getViolations(Advantage advantage){
            if(this.violationMap.containsKey(advantage)){
                return violationMap.get(advantage);
            }

            this.violationMap.put(advantage, 0);
            return 0;
        }

        public void setViolationMap(HashMap<Advantage, Integer> violationMap) {
            this.violationMap = violationMap;
        }

        public void incrementAdvantage(Advantage advantage, int modification) {
            int violations = violationMap.getOrDefault(advantage, 0);
            if (violations + modification < 0) {
                violations = 0;
            } else {
                violations += modification;
            }

            violationMap.put(advantage, violations);
        }
    }
}
