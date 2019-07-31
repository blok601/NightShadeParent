package com.nightshadepvp.tournament.cmd.cmds.staff.whitelist;

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
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : GameHandler.getInstance().getWhitelist()) {
            stringBuilder.append(s).append(", ");
        }

        String list = stringBuilder.toString().trim();
        String sub = list.substring(0, list.length() - 1);
        tPlayer.msg("&eWhitelited Players&8: &3" + sub);
    }
}
