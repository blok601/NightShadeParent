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

    public ArenaSession() {
        this.start = new Timestamp(System.currentTimeMillis());
        this.killstreak = 0;
        this.kills = 0;
        this.deaths = 0;
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
}
