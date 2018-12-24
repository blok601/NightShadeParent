package com.nightshadepvp.core.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

/**
 * Created by Blok on 9/4/2018.
 */
public class CmdTeamSpeak extends NightShadeCoreCommand {

    private static CmdTeamSpeak i =new CmdTeamSpeak();
    public static CmdTeamSpeak get() {return i;}

    public CmdTeamSpeak() {
        this.addAliases("teamspeak", "ts");
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        nsPlayer.msg(ChatUtils.message("&cCome on... who still uses TeamSpeak? &eJoin our discord&8: &bdiscord.me/NightShadeMC"));
    }

}
