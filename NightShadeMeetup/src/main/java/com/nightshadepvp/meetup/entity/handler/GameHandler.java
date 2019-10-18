package com.nightshadepvp.meetup.entity.handler;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nightshadepvp.meetup.GameState;
import com.nightshadepvp.meetup.entity.object.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Created by Blok on 7/31/2019.
 */
public class GameHandler {

    private Map currentMap;
    private GameState gameState;
    private HashSet<UUID> playing;
    private boolean chatFrozen;

    private HashMap<UUID, Consumer<Player>> taskQueue;


    public GameHandler() {
        this.currentMap = new Map(500, Bukkit.getWorlds().get(1)); //This will change later (default map)
        this.gameState = GameState.WAITING;
        this.playing = Sets.newHashSet();
        this.chatFrozen = false;
        this.taskQueue = Maps.newHashMap();
    }

    public Map getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(Map currentMap) {
        this.currentMap = currentMap;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean gameHasStarted() {
        return this.gameState == GameState.INGAME || this.gameState == GameState.ENDING;
    }

    public HashSet<UUID> getPlaying() {
        return playing;
    }

    public boolean isChatFrozen() {
        return chatFrozen;
    }

    public void setChatFrozen(boolean chatFrozen) {
        this.chatFrozen = chatFrozen;
    }

    public HashMap<UUID, Consumer<Player>> getTaskQueue() {
        return taskQueue;
    }
}
