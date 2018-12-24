package com.nightshadepvp.core.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

/**
 * Created by Blok on 9/9/2018.
 */
public class CmdMatchpost extends NightShadeCoreCommand {

    private static CmdMatchpost i = new CmdMatchpost();
    public static CmdMatchpost get() {
        return i;
    }

    public CmdMatchpost() {
        this.addAliases("matchpost", "post", "viewmatchpost");
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        nsPlayer.msg(ChatUtils.message("&eThe current matchpost is&8: &3" + Core.get().getMatchpost()));
    }
}
