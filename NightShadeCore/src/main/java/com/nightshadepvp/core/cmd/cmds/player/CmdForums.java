package com.nightshadepvp.core.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

/**
 * Created by Blok on 9/4/2018.
 */
public class CmdForums extends NightShadeCoreCommand {

    private static CmdForums i = new CmdForums();
    public static CmdForums get() {return i;}

    public CmdForums() {
        this.addAliases("website", "forums", "apply");
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        nsPlayer.msg(ChatUtils.message("&eOur website is&8: &bhttp://www.nightshadepvp.com"));
        nsPlayer.msg(ChatUtils.message("&eYou can apply @ &bhttp://bit.ly/ns_apply"));
    }
}
