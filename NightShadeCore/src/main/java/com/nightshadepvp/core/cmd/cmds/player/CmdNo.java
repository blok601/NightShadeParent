package com.nightshadepvp.core.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.cmds.staff.CmdStartVote;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

/**
 * Created by Blok on 11/18/2018.
 */
public class CmdNo extends NightShadeCoreCommand {

    public static CmdNo i = new CmdNo();

    public static CmdNo get() {
        return i;
    }

    public CmdNo() {
        this.addAliases("no");
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        if (!CmdStartVote.get().voteRunning) {
            nsPlayer.msg(ChatUtils.message("&cThere is no vote running!"));
            return;
        }

        if (CmdStartVote.get().voted.contains(nsPlayer.getUuid())) {
            nsPlayer.msg(ChatUtils.message("&cYou have already voted!"));
            return;
        }

        CmdStartVote.get().NO_VOTES++;
        nsPlayer.msg(ChatUtils.message("&eYou have voted &cno&e!. Thanks for voting!"));
    }
}
