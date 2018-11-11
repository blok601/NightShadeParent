package me.blok601.nightshadeuhc.stats;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Blok on 8/18/2018.
 */
public class CachedGame {

    private String host;
    private List<String> winners;
    private List<String> scenarios;
    private int fill;
    private HashMap<String, Integer> winnerKills;
    private String teamType;
    private Timestamp start;
    private Timestamp end;
    private String server;
    private long matchID;

    public CachedGame(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<String> getWinners() {
        return winners;
    }

    public void setWinners(List<String> winners) {
        this.winners = winners;
    }

    public List<String> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<String> scenarios) {
        this.scenarios = scenarios;
    }

    public int getFill() {
        return fill;
    }

    public void setFill(int fill) {
        this.fill = fill;
    }

    public HashMap<String, Integer> getWinnerKills() {
        return winnerKills;
    }

    public void setWinnerKills(HashMap<String, Integer> winnerKills) {
        this.winnerKills = winnerKills;
    }

    public String getTeamType() {
        return teamType;
    }

    public void setTeamType(String teamType) {
        this.teamType = teamType;
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

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public long getMatchID() {
        return matchID;
    }

    public void setMatchID(long matchID) {
        this.matchID = matchID;
    }
}
