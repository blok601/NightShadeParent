package com.nightshadepvp.tournament.cmd.cmds.admin;

import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.utils.ChatUtils;

public class CmdWipeHost extends NightShadeTournamentCommand {

    private static CmdWipeHost i = new CmdWipeHost();
    public static CmdWipeHost get() {return i;}

    public CmdWipeHost() {
        this.addAliases("wipehost");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.OWNER));
        this.addParameter(TypeNSPlayer.get(), "target");
    }

    @Override
    public void perform() throws MassiveException {
        TPlayer tPlayer = TPlayer.get(sender);
        NSPlayer target = this.readArg();
        if(target == null || !target.hasPlayedBefore()){
            tPlayer.msg(ChatUtils.message("&cThat player couldn't be found!"));
            return;
        }

        TPlayer tPlayerTarget = TPlayer.get(target.getUuid());
        if(tPlayerTarget == null){
            tPlayer.msg(ChatUtils.message("&cThat player couldn't be found!"));
            return;
        }

        if(tPlayerTarget.getTournamentsHosted() == 0){
            tPlayer.msg(ChatUtils.message("&cThat player has never hosted a Tournament!"));
            return;
        }

        tPlayerTarget.setTournamentsHosted(0);
        tPlayer.msg(ChatUtils.message("&bSuccessfully reset &f" + tPlayerTarget.getName() + "'s &bhosting counter"));
    }
}
