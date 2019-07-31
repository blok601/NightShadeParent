package com.nightshadepvp.tournament.cmd.cmds.staff;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;

/**
 * Created by Blok on 7/27/2018.
 */
public class CmdSpectator extends NightShadeTournamentCommand {

    private static CmdSpectator i = new CmdSpectator();

    public static CmdSpectator get() {
        return i;
    }

    public CmdSpectator() {
        this.addAliases("spectator", "spec");
        this.addRequirements(RequirementIsPlayer.get(), ReqRankHasAtLeast.get(Rank.TRIAL));
    }

    @Override
    public void perform() throws MassiveException {
        TPlayer tPlayer = TPlayer.get(sender);
        if (tPlayer.isSpectator()) {
            tPlayer.unspec();
        } else {
            tPlayer.spec();
        }
    }
}
