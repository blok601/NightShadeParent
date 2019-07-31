package com.nightshadepvp.tournament.entity.handler;

import com.nightshadepvp.tournament.entity.objects.game.iMatch;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Blok on 7/18/2018.
 */
public class RoundHandler {
    private static RoundHandler ourInstance = new RoundHandler();

    public static RoundHandler getInstance() {
        return ourInstance;
    }

    private HashMap<Integer, HashSet<iMatch>> matches;
    private int round;

    private RoundHandler() {

    }

    public void setup() {
        matches = new HashMap<>();
        round = 1;
    }

    public HashSet<iMatch> getMatchesByRoundNumber(int round) {
        if (matches.containsKey(round)) {
            return matches.get(round);
        } else {
            return new HashSet<>();
        }
    }

    public void incrementRound() {
        this.round++;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void populateRound(int round) {
        matches.put(round, new HashSet<>());
    }

    public void addMatch(int r, iMatch soloMatch) {
        getMatchesByRoundNumber(r).add(soloMatch);
        MatchHandler.getInstance().setMatchID(MatchHandler.getInstance().getMatchID() + 1);
        soloMatch.setMatchID(MatchHandler.getInstance().getMatchID());
    }
}
