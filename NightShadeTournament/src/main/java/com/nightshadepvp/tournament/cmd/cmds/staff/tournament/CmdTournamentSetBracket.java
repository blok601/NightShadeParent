package com.nightshadepvp.tournament.cmd.cmds.staff.tournament;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.handler.GameHandler;
import com.nightshadepvp.tournament.utils.ChatUtils;

/**
 * Created by Blok on 7/18/2018.
 */
public class CmdTournamentSetBracket extends NightShadeTournamentCommand {

    public CmdTournamentSetBracket() {
        this.addAliases("setbracket");
        this.addParameter(TypeString.get(), "bracket link");
        this.addRequirements(RequirementIsPlayer.get(), ReqRankHasAtLeast.get(Rank.TRIAL));
    }

    @Override
    public void perform() throws MassiveException {
        TPlayer tPlayer = TPlayer.get(sender);
        String link = this.readArg();

        if(!link.contains("challonge")){
            tPlayer.msg(ChatUtils.message("&cInvalid bracket link!"));
            return;
        }

        GameHandler.getInstance().setBracketLink(link);
        tPlayer.msg(ChatUtils.message("&eThe bracket link has been updated to &b" + link));
    }
}
