package com.nightshadepvp.tournament.cmd.cmds.staff.tournament;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.tournament.Tournament;
import com.nightshadepvp.tournament.challonge.Challonge;
import com.nightshadepvp.tournament.challonge.GameType;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.handler.GameHandler;
import com.nightshadepvp.tournament.utils.ChatUtils;

import java.util.concurrent.ExecutionException;

/**
 * Created by Blok on 4/10/2020.
 */
public class CmdTournamentPost extends NightShadeTournamentCommand {

    public CmdTournamentPost() {
        this.addAliases("post");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL), RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        TPlayer tPlayer = TPlayer.get(sender);
        GameHandler gameHandler = GameHandler.getInstance();

        if (gameHandler.getHost() == null) {
            tPlayer.msg(ChatUtils.message("&cThe host must be set and online to post the bracket link!"));
            return;
        }

        if (gameHandler.getKit() == null) {
            tPlayer.msg(ChatUtils.message("&cYou must set the kit before posting the bracket link!"));
            return;
        }

        int timesHosted = TPlayer.get(gameHandler.getHost()).getTournamentsHosted() + 1;
        String suffix= "";
        if(timesHosted <= 10){
            if(timesHosted == 1){
                suffix = "st";
            }else if(timesHosted == 2){
                suffix = "nd";
            }else if(timesHosted == 3){
                suffix = "rd";
            }else{
                suffix = "th";
            }
        }else if(timesHosted > 10 && timesHosted < 100){
            if(timesHosted % 10 == 1){
                suffix = "st";
            }else if(timesHosted % 10 == 2){
                suffix = "nd";
            }else if(timesHosted % 10 == 3){
                suffix = "rd";
            }else{
                suffix = "th";
            }
        }
        String tourneyName = gameHandler.getHost().getName() + "'s " + timesHosted + "" + suffix + " Tournament";
        Challonge challonge = new Challonge("pSsOoEKn0mpSHDVLKaexohUGbmvLIOeqFpXjPbxy", "NightShadePvP", "ns" + System.currentTimeMillis(), tourneyName, "PvP Tournament on NightShadePvP with " + gameHandler.getKit().getName() + " kit", GameType.SINGLE);
        tPlayer.msg(ChatUtils.message("&bPosting bracket..."));
        try {
            if (challonge.post().get()) {
                tPlayer.msg(ChatUtils.message("&fBracket successfully posted. You can now view it at: &b" + challonge.getUrl()));
                Tournament.get().setChallonge(challonge);
                gameHandler.setBracketLink(challonge.getUrl());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            tPlayer.msg(ChatUtils.message("&cThere was an error trying to post the bracket! Make sure all the settings have been set..."));
            tPlayer.msg(ChatUtils.message("&cIf the problem persists, contact an administrator."));
        }
    }
}