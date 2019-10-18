package com.nightshadepvp.core.cmd.cmds.server;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsntPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.entity.NSPlayerColl;
import com.nightshadepvp.core.utils.ChatUtils;

/**
 * Created by Blok on 7/6/2018.
 */
public class CmdAcNotify extends NightShadeCoreCommand{

    private static CmdAcNotify i = new CmdAcNotify();
    public static CmdAcNotify get() {return i;}

    public CmdAcNotify() {
        this.addAliases("acnotify");
        this.addParameter(TypeString.get(), "message", true);
        this.addRequirements(RequirementIsntPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        String msg = this.readArg();
        NSPlayerColl.get().getAllOnline().forEach(o -> {
            if (o.hasRank(Rank.TRIAL))  {
                o.msg(ChatUtils.format(msg));
            }
        });
    }
}
