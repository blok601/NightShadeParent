package com.nightshadepvp.tournament.cmd.cmds.staff.whitelist;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.handler.GameHandler;
import com.nightshadepvp.tournament.utils.ChatUtils;

/**
 * Created by Blok on 8/8/2018.
 */
public class CmdWhitelistRemove extends NightShadeTournamentCommand {

    public CmdWhitelistRemove() {
        this.addAliases("remove");
        this.addParameter(TypeString.get(), "player/*");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));
    }

    @Override
    public void perform() throws MassiveException {
        TPlayer tPlayer = TPlayer.get(sender);
        String type = this.readArg();
        if(type.equalsIgnoreCase("*") || type.equalsIgnoreCase("all")){
            GameHandler.getInstance().getWhitelist().clear();
            tPlayer.msg(ChatUtils.message("&bSuccessfully cleared the whitelist!"));
            return;
        }

        if(GameHandler.getInstance().getWhitelist().contains(type.toLowerCase())){
            GameHandler.getInstance().getWhitelist().remove(type.toLowerCase());
            tPlayer.msg(ChatUtils.message("&bRemoved &f" + type + " &bfrom the whitelist!"));
            return;
        }

        tPlayer.msg(ChatUtils.message("&cThat player isn't on the whitelist!"));
    }
}
