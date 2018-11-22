package me.blok601.nightshadeuhc.stats;

import java.util.List;
import java.util.Map;

/**
 * Created by Blok on 8/18/2018.
 */
public class CachedGame {

    private String host;
    private List<String> winners;
    private List<String> scenarios;
    private int fill;
    private Map<String, Integer> winnerKills;
    private String teamType;
    private long start;
    private long end;
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

    public Map<String, Integer> getWinnerKills() {
        return winnerKills;
    }

    public void setWinnerKills(Map<String, Integer> winnerKills) {
        this.winnerKills = winnerKills;
    }

    public String getTeamType() {
        return teamType;
    }

    public void setTeamType(String teamType) {
        this.teamType = teamType;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
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
