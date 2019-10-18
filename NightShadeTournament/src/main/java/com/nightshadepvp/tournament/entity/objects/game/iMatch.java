package com.nightshadepvp.tournament.entity.objects.game;

import com.nightshadepvp.core.fanciful.FancyMessage;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.enums.MatchState;
import com.nightshadepvp.tournament.entity.objects.data.Arena;
import com.nightshadepvp.tournament.scoreboard.type.Scoreboard;
import com.nightshadepvp.tournament.task.LogOutTimerTask;
import org.bukkit.Location;
import org.bukkit.event.entity.EntityEvent;

import java.util.*;

/**
 * Created by Blok on 7/26/2018.
 */
public interface iMatch {
    /**
     *
     * @return Global SoloMatch ID
     */
    int getMatchID();

    /**
     *
     * @return Team 1 Players
     */
    List<TPlayer> getTeam1();

    /**
     *
     * @return Team 2 Players
     */
    List<TPlayer> getTeam2();

    /**
     * @return Current Arena
     */
    Arena getArena();

    /**
     * @return Winners
     */
    List<TPlayer> getWinners();

    /**
     *
     * @return Current SoloMatch State
     */
    MatchState getMatchState();

    /**
     *
     * @return SoloMatch Timer
     */
    String getTimer();

    /**
     *
     * @return Player scoreboards
     */
    HashMap<UUID, Scoreboard> getScoreboards();

    /**
     * Get winning players
     * @param winners players who won
     */
    void endMatch(List<TPlayer> winners, EntityEvent event);

    /**
     *
     * @return All players in match
     */
    List<TPlayer> getPlayers();

    /**
     * Send a message to all players
     * @param message Message to be broadcasted
     */
    void broadcast(String message);

    /**
     * Broadcasts message to all players and spectators
     * @param message message to be broadcasted
     */
    void broadcastAll(String message);

    /**
     * Broadcast a FancyMessage to all players
     * @param fancyMessage FancyMessage to broadcast
     */
    void broadcastFancy(FancyMessage fancyMessage);

    /**
     * Broadcast a FancyMessage to all players and spectators
     * @param fancyMessage FancyMessage to broadcast
     */
    void broadcastAllFancy(FancyMessage fancyMessage);

    /**
     * Broadcast a formatted message to players
     */
    void broadcastFormat(String message);

    /**
     * Broadcast a formatted to all players and specs
     */
    void broadcastAllFormat(String message);

    /**
     * Set the match ID
     *
     * @param id id to set
     */
    void setMatchID(int id);

    /**
     * Start the match
     */
    void start();

    /**
     * Add a spectator to the list of spectators
     * @param tPlayer TPlayer to add to spectators
     */
    void addSpectator(TPlayer tPlayer);

    /**
     *
     * @return Set of spectators
     */
    Set<TPlayer> getSpectators();

    /**
     * Get safe, teleportable location in match
     * @return Teleportable Location in match
     */
    Location getTeleportableLocation();

    /**
     * Start the logout timer for a player
     * @param tPlayer player to start the logout timer for
     */
    void startLogOutTimer(TPlayer tPlayer);

    /**
     * Get the logout timer for a player
     * @param tPlayer player to get the logout timer for
     * @return
     */
    LogOutTimerTask getLogOutTimer(TPlayer tPlayer);

    /**
     * Get the opponents of a player
     * @param tPlayer Player to get opponent(s) of
     * @return List of opponent(s)
     */
    List<TPlayer> getOpponents(TPlayer tPlayer);

    /**
     * Remove a logout timer of a player
     * @param tPlayer Tplayer to remove the timer from
     */
    void stopLogOutTimer(TPlayer tPlayer);

    /**
     * Blocks placed in the match
     * @return Location of the blocks placed in the match
     */
    HashSet<Location> getBlocks();

    /**
     * Reset the placed blocks
     */
    void resetBlocks();

    /**
     * Get the starting milliseconds
     * @return The starting milliseconds
     */
    long getStartTimeMillis();
}
