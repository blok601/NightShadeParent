package me.blok601.nightshadeuhc.stats;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Blok on 8/18/2018.
 */
public class CachedGame {

    private UUID host;
    private List<UUID> winners;
    private List<String> scenarios;
    private int fill;
    private HashMap<UUID, Integer> winnerKills;
    private String teamType;
    private Timestamp start;
    private Timestamp end;
    private String server;
    private long matchID;

    public CachedGame(UUID host) {
        this.host = host;
    }

    public UUID getHost() {
        return host;
    }

    public void setHost(UUID host) {
        this.host = host;
    }

    public List<UUID> getWinners() {
        return winners;
    }

    public void setWinners(List<UUID> winners) {
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

    public HashMap<UUID, Integer> getWinnerKills() {
        return winnerKills;
    }

    public void setWinnerKills(HashMap<UUID, Integer> winnerKills) {
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
