package com.nightshadepvp.meetup.entity.handler;

import com.nightshadepvp.meetup.GameState;
import com.nightshadepvp.meetup.Meetup;
import com.nightshadepvp.meetup.entity.object.Map;
import org.bukkit.Bukkit;

/**
 * Created by Blok on 10/15/2018.
 */
public class GameHandler {

    private Meetup plugin;

    public GameHandler(Meetup plugin){
        this.plugin = plugin;
        this.maxPlayers = 12;
        this.initialBorder = 300;
        this.gameState = GameState.WAITING;
        this.map = new Map(Bukkit.getWorlds().get(0), 500); //TODO: Change this later on
    }

    private int maxPlayers;
    private int initialBorder;
    private GameState gameState;
    private Map map;

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getInitialBorder() {
        return initialBorder;
    }

    public void setInitialBorder(int initialBorder) {
        this.initialBorder = initialBorder;
    }

    public GameState getGameState() {
        return gameState;
    }

    public boolean inGame(){
        return this.gameState == GameState.INGAME; // if in game -> will return true
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
