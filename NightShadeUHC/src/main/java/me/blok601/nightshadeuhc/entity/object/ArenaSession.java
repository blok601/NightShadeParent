package me.blok601.nightshadeuhc.entity.object;

import java.sql.Timestamp;
import java.text.DecimalFormat;

/**
 * Created by Blok on 12/24/2018.
 */
public class ArenaSession {

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("##.##");

    private Timestamp start;
    private Timestamp end;
    private int killstreak;
    private int kills;
    private int deaths;

    private int gapplesEaten;
    private int bowAttempts;
    private int bowHits;
    private int swordSwings;
    private int swordHits;

    public ArenaSession() {
        this.start = new Timestamp(System.currentTimeMillis());
        this.killstreak = 0;
        this.kills = 0;
        this.deaths = 0;
        this.gapplesEaten = 0;
        this.bowAttempts = 0;
        this.bowHits = 0;
        this.swordSwings = 0;
        this.swordHits = 0;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public int getKillstreak() {
        return killstreak;
    }

    public void setKillstreak(int killstreak) {
        this.killstreak = killstreak;
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

    public double getKDR(){
        if (this.kills == 0) {
            return 0;
        }

        if (this.deaths == 0) {
            return this.deaths;
        }

        return this.kills / this.deaths;
    }

    public int getGapplesEaten() {
        return gapplesEaten;
    }

    public void setGapplesEaten(int gapplesEaten) {
        this.gapplesEaten = gapplesEaten;
    }

    public int getBowAttempts() {
        return bowAttempts;
    }

    public void setBowAttempts(int bowAttemps) {
        this.bowAttempts = bowAttemps;
    }

    public int getBowHits() {
        return bowHits;
    }

    public void setBowHits(int bowHits) {
        this.bowHits = bowHits;
    }

    public int getSwordSwings() {
        return swordSwings;
    }

    public void setSwordSwings(int swordSwings) {
        this.swordSwings = swordSwings;
    }

    public int getSwordHits() {
        return swordHits;
    }

    public void setSwordHits(int swordHits) {
        this.swordHits = swordHits;
    }

}
