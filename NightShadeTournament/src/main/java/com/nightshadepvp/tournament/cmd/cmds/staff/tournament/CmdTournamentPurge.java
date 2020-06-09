package com.nightshadepvp.tournament.cmd.cmds.staff.tournament;

import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;

public class CmdTournamentPurge extends NightShadeTournamentCommand {

    public CmdTournamentPurge() {
        this.addAliases("purge");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.ADMIN));
    }

    @Override
    public void perform() throws MassiveException {

    }
}
