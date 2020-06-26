package com.nightshadepvp.tournament.entity.objects.data;

import java.util.List;
import java.util.Map;

public class CachedGame {

    private String host;
    private List<String> winners;
    private int fill;
    private String teamType;
    private long start;
    private long end;
    private String server;
    private long tourneyID;
    private String bracketLink;
    private String kit;
    private int rounds;

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

    public int getFill() {
        return fill;
    }

    public void setFill(int fill) {
        this.fill = fill;
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


    public String getBracketLink() {
        return bracketLink;
    }

    public void setBracketLink(String bracketLink) {
        this.bracketLink = bracketLink;
    }

    public long getTourneyID() {
        return tourneyID;
    }

    public void setTourneyID(long tourneyID) {
        this.tourneyID = tourneyID;
    }

    public String getKit() {
        return kit;
    }

    public void setKit(String kit) {
        this.kit = kit;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }
}
