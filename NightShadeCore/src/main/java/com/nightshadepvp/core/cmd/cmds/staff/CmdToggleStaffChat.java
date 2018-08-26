package com.nightshadepvp.core.cmd.cmds.staff;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

/**
 * Created by Blok on 8/24/2018.
 */
public class CmdToggleStaffChat extends NightShadeCoreCommand {

    private static CmdToggleStaffChat i = new CmdToggleStaffChat();

    public static CmdToggleStaffChat get() {
        return i;
    }

    public CmdToggleStaffChat() {
        this.addAliases("togglestaffchat", "togglesc");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        if(nsPlayer.isReceivingStaffChat()){
            nsPlayer.setReceivingStaffChat(false);
            nsPlayer.message(ChatUtils.message("&eYou are no longer receiving staff chat."));
            return;
        }

        nsPlayer.setReceivingStaffChat(true);
        nsPlayer.msg(ChatUtils.message("&eYou are now receiving staff chat."));
    }
}
