package com.nightshadepvp.tournament.cmd.cmds.staff;

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
public class CmdFreezeChat extends NightShadeTournamentCommand {

    private static CmdFreezeChat i = new CmdFreezeChat();
    public static CmdFreezeChat get() {return i;}

    public CmdFreezeChat() {
        this.addAliases("freezechat", "cstop");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));
    }

    @Override
    public void perform() throws MassiveException {
        TPlayer tPlayer = TPlayer.get(sender);
        if(GameHandler.getInstance().isChatFrozen()){
            GameHandler.getInstance().setChatFrozen(false);
            tPlayer.msg(ChatUtils.message("&bChat is now&8: &aunfrozen"));
            return;
        }

        GameHandler.getInstance().setChatFrozen(true);
        tPlayer.msg(ChatUtils.message("&bChat is now&8: &cfrozen"));
    }
}
