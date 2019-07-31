package com.nightshadepvp.tournament.cmd.cmds.staff.whitelist;

import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.handler.GameHandler;
import com.nightshadepvp.tournament.utils.ChatUtils;

/**
 * Created by Blok on 8/8/2018.
 */
public class CmdWhitelistOff extends NightShadeTournamentCommand {

    public CmdWhitelistOff() {
        this.addAliases("off");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));
    }

    @Override
    public void perform() throws MassiveException {
        TPlayer tPlayer = TPlayer.get(sender);
        GameHandler.getInstance().setWhitelistOn(false);
        tPlayer.msg(ChatUtils.message("&eThe whitelist is now&8: &coff"));
    }
}
