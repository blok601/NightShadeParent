package me.blok601.nightshadeuhc.manager;

import me.blok601.nightshadeuhc.entity.object.CombatLogger;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Blok on 9/23/2017.
 */
public class LoggerManager {


    private static LoggerManager instance = new LoggerManager();

    public static LoggerManager getInstance() {
        return instance;
    }

    private ArrayList<CombatLogger> loggers = new ArrayList<>();

    public ArrayList<CombatLogger> getLoggers() {
        return loggers;
    }

    public CombatLogger getLogger(String logger) {
        for (CombatLogger combatLogger : loggers) {
            if (combatLogger.getLoggerName().equalsIgnoreCase(logger)) {
                return combatLogger;
            }
        }
        return null;
    }

    public CombatLogger getLogger(int id) {
        for (CombatLogger logger : loggers) {
            if (logger.getArmorStand().getEntityId() == id) {
                return logger;
            }
        }

        return null;
    }

    public void createLogger(CombatLogger logger) {
        loggers.add(logger);
    }

    public void removeLogger(CombatLogger logger) {
        loggers.remove(logger);
    }

    private ArrayList<UUID> deadLoggers = new ArrayList<>();

    public ArrayList<UUID> getDeadLoggers() {
        return deadLoggers;
    }
}