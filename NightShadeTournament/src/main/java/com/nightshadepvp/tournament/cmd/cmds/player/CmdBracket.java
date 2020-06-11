package com.nightshadepvp.tournament.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.tournament.Tournament;
import com.nightshadepvp.tournament.challonge.Challonge;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.utils.ChatUtils;

public class CmdBracket extends NightShadeTournamentCommand {

    private static CmdBracket i = new CmdBracket();
    public static CmdBracket get() {return i;}

    public CmdBracket() {
        this.addAliases("bracket");
    }

    @Override
    public void perform() throws MassiveException {
        TPlayer tPlayer = TPlayer.get(sender);
        Challonge challonge = Tournament.get().getChallonge();

        if(challonge == null){
            tPlayer.msg(ChatUtils.message("&cThe bracket hasn't been posted yet!"));
            return;
        }

        tPlayer.msg(ChatUtils.message("&bView the bracket here: &f" + challonge.getUrl()));
    }
}
