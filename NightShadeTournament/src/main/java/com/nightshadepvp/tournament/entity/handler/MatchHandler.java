package com.nightshadepvp.tournament.entity.handler;

import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.enums.MatchState;
import com.nightshadepvp.tournament.entity.objects.game.SoloMatch;
import com.nightshadepvp.tournament.entity.objects.game.iMatch;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Blok on 7/18/2018.
 */
public class MatchHandler {
    private static MatchHandler ourInstance = new MatchHandler();

    public static MatchHandler getInstance() {
        return ourInstance;
    }

    private ArrayList<iMatch> matches;
    private int matchID;

    private MatchHandler() {
        matches = new ArrayList<>();
        matchID = 0;
    }

    public void addMatch(TPlayer player1, TPlayer player2){
        SoloMatch soloMatch = new SoloMatch(player1, player2);
        matchID++;
        soloMatch.setMatchID(matchID);
        matches.add(soloMatch);
    }

    public void addMatch(SoloMatch soloMatch){
        matchID++;
        soloMatch.setMatchID(matchID);
        matches.add(soloMatch);
    }

    public iMatch getMatch(int id){
        return this.matches.stream().filter(iMatch -> iMatch.getMatchID() == id).findFirst().orElse(null);
    }


    public List<iMatch> getMatches(TPlayer tPlayer){
        return this.matches.stream().filter(match -> match.getPlayers().contains(tPlayer)).collect(Collectors.toList());
    }

    public List<iMatch> getMatches(Player player){
        TPlayer tPlayer = TPlayer.get(player);
        return this.matches.stream().filter(match -> match.getPlayers().contains(tPlayer)).collect(Collectors.toList());
    }

    public iMatch getActiveMatch(TPlayer tPlayer){
        return this.matches.stream().filter(match -> match.getPlayers().contains(tPlayer)).filter(iMatch -> iMatch.getMatchState() != MatchState.DONE).findFirst().orElse(null);
    }

    public iMatch getActiveMatch(Player player){
        TPlayer tPlayer = TPlayer.get(player);
        return this.matches.stream().filter(match -> match.getPlayers().contains(tPlayer)).filter(iMatch -> iMatch.getMatchState() != MatchState.DONE).findFirst().orElse(null);
    }

    public List<iMatch> getActiveMatches(){
        return this.matches.stream().filter(iMatch -> iMatch.getMatchState() != MatchState.DONE).collect(Collectors.toList());
    }

    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }
}
