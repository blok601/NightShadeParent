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
public class CmdWhitelistClear extends NightShadeTournamentCommand {

    public CmdWhitelistClear() {
        this.addAliases("clear");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));
    }

    @Override
    public void perform() throws MassiveException {
        TPlayer tPlayer = TPlayer.get(sender);
        GameHandler.getInstance().getWhitelist().clear();
        GameHandler.getInstance().setWhitelistOn(false);
        tPlayer.msg(ChatUtils.message("&bCleared and Disabled the whitelist!"));
    }
}
