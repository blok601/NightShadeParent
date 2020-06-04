package com.nightshadepvp.tournament.challonge;

import java.util.concurrent.ExecutionException;

/**
 * Created by Blok on 4/11/2020.
 */
public class MatchWrapper {

    public static String[] getPlayerNames(int matchID, Challonge challonge) {
        try {
            Integer[] partids = challonge.getMatchParticipants(challonge.getMatchIdFromChallongeId(String.valueOf(matchID))).get();
            return new String[]{
                    challonge.getNameFromId(partids[0]),
                    challonge.getNameFromId(partids[1])
            };
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }


}
