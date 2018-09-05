package com.nightshadepvp.core.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

/**
 * Created by Blok on 9/4/2018.
 */
public class CmdDiscord extends NightShadeCoreCommand {

    private static CmdDiscord i =new CmdDiscord();
    public static CmdDiscord get() {return i;}

    public CmdDiscord() {
        this.addAliases("discord");
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        nsPlayer.msg(ChatUtils.message("&eOur discord&8: &bdiscord.me/NightShadeMC"));
    }
}
