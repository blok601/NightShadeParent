package com.nightshadepvp.meetup.entity;

import com.massivecraft.massivecore.store.SenderEntity;
import com.massivecraft.massivecore.util.MUtil;

/**
 * Created by Blok on 10/15/2018.
 */
public class MPlayer extends SenderEntity<MPlayer> {

    // -------------------------------------------- //
    // Stats
    // -------------------------------------------- //

    private int gamesPlayed = 0;
    private int gamesWon = 0;
    private int kills = 0;
    private int deaths = 0;
    private int highestKillStreak = 0;

    // -------------------------------------------- //
    // INGAME
    // -------------------------------------------- //

    private transient boolean spectator;
    private transient int gameKills;
    private transient int killStreak;


    public static MPlayer get(Object oid) {
        return MPlayerColl.get().get(oid);
    }

    @Override
    public MPlayer load(MPlayer that) {
        this.setLastActivityMillis(that.lastActivityMillis);

        this.setGamesPlayed(that.gamesPlayed);
        this.setGamesWon(that.gamesWon);
        this.setKills(that.kills);
        this.setDeaths(that.deaths);
        this.setHighestKillStreak(that.highestKillStreak);

        return this;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getHighestKillStreak() {
        return highestKillStreak;
    }

    public void setHighestKillStreak(int highestKillStreak) {
        this.highestKillStreak = highestKillStreak;
    }

    private long lastActivityMillis = System.currentTimeMillis();

    public void setLastActivityMillis(long lastActivityMillis) {
        // Clean input
        long target = lastActivityMillis;

        // Detect Nochange
        if (MUtil.equals(this.lastActivityMillis, target)) return;

        // Apply
        this.lastActivityMillis = target;

        // Mark as changed
        this.changed();
    }

    public int getGameKills() {
        return gameKills;
    }

    public void setGameKills(int gameKills) {
        this.gameKills = gameKills;
    }

    public int getKillStreak() {
        return killStreak;
    }

    public void setKillStreak(int killStreak) {
        this.killStreak = killStreak;
    }

    public boolean isSpectator() {
        return spectator;
    }

    public void setSpectator(boolean spectator) {
        this.spectator = spectator;
    }
}
