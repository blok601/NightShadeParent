package com.nightshadepvp.core.cmd.cmds.admin.announcer;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanOn;
import com.nightshadepvp.core.Announcer;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

/**
 * Created by Blok on 3/2/2019.
 */
public class CmdAnnouncer extends NightShadeCoreCommand {

    private static CmdAnnouncer i = new CmdAnnouncer();

    public static CmdAnnouncer get() {
        return i;
    }


    public CmdAnnouncer() {
        this.addAliases("announcer", "acc");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.ADMIN));
        this.addParameter(TypeBooleanOn.get(), "toggle");
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        boolean arg = this.readArg();
        Announcer announcer = Core.get().getAnnouncer();
        if (announcer.isRunning() == arg) {
            nsPlayer.msg(ChatUtils.message("&cThe announcer is already " + (arg ? "&aon" : "off")));
            return;
        }

        announcer.setRunning(arg);
        nsPlayer.msg(ChatUtils.message("&eThe announcer is now " + (arg ? "&aon" : "&coff")));
    }
}
