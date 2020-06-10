package com.nightshadepvp.tournament.cmd.cmds.staff.whitelist;

import com.google.common.base.Joiner;
import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.handler.GameHandler;

/**
 * Created by Blok on 8/8/2018.
 */
public class CmdWhitelistList extends NightShadeTournamentCommand {

    public CmdWhitelistList() {
        this.addAliases("list");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));
    }

    @Override
    public void perform() throws MassiveException {
        TPlayer tPlayer = TPlayer.get(sender);

        String l = Joiner.on("&7,&b ").join(GameHandler.getInstance().getWhitelist());
        tPlayer.msg("&bWhitelited Players&8: &b" + l);
    }
}
