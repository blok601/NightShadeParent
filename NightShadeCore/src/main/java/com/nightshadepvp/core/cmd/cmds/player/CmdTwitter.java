package com.nightshadepvp.core.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

/**
 * Created by Blok on 9/4/2018.
 */
public class CmdTwitter extends NightShadeCoreCommand {

    private static CmdTwitter i = new CmdTwitter();
    public static CmdTwitter get() {return i;}

    public CmdTwitter() {
        this.addAliases("twitter");
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        nsPlayer.msg(ChatUtils.message("&eOur twitter is&8: &b@NightShadePvPMC"));
    }
}
