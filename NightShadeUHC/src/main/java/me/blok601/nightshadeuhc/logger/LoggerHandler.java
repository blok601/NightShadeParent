package me.blok601.nightshadeuhc.logger;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Blok on 9/23/2017.
 */
public class LoggerHandler {


    private static LoggerHandler instance = new LoggerHandler();

    public static LoggerHandler getInstance() {
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