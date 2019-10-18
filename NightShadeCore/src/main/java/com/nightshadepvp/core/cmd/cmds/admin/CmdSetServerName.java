package com.nightshadepvp.core.cmd.cmds.admin;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.MConf;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

/**
 * Created by Blok on 8/24/2018.
 */
public class CmdSetServerName extends NightShadeCoreCommand {

    private static CmdSetServerName i = new CmdSetServerName();
    public static CmdSetServerName get() {
        return i;
    }

    public CmdSetServerName() {
        this.addAliases("setservername");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.OWNER));
        this.addParameter(TypeString.get(), "name");
        this.setVisibility(Visibility.SECRET);
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        String string = this.readArg();
        MConf.get().setServerName(string);
        nsPlayer.msg(ChatUtils.message("&eUpdated server name to&8: &3" + string));
    }
}
