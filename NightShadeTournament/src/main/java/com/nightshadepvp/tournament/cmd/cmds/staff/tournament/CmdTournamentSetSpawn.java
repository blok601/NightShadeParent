package com.nightshadepvp.tournament.cmd.cmds.staff.tournament;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.tournament.Tournament;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.utils.ChatUtils;

/**
 * Created by Blok on 7/25/2018.
 */
public class CmdTournamentSetSpawn extends NightShadeTournamentCommand {

    public CmdTournamentSetSpawn() {
        this.addAliases("setspawn");
        this.addRequirements(RequirementIsPlayer.get(), ReqRankHasAtLeast.get(Rank.ADMIN));
    }

    @Override
    public void perform() throws MassiveException {
        TPlayer tPlayer = TPlayer.get(sender);
        Tournament.get().setSpawnLocation(tPlayer.getPlayer().getLocation());
        tPlayer.msg(ChatUtils.message("&bUpdated the server spawn!"));
    }
}
