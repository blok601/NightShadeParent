package com.nightshadepvp.core.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

/**
 * Created by Blok on 7/6/2018.
 */
public class CmdTogglePM extends NightShadeCoreCommand{

    private static CmdTogglePM i = new CmdTogglePM();
    public static CmdTogglePM get() {return i;}


    public CmdTogglePM() {
        this.addAliases("togglepm", "pmtoggle");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer user = NSPlayer.get(sender);

        user.setReceivingPMs(!user.isReceivingPMs());

        if(user.isReceivingPMs()){
            user.getPlayer().sendMessage(ChatUtils.message("&aPrivate messages have been enabled!"));
            return;
        }

        user.getPlayer().sendMessage(ChatUtils.message("&cPrivate messages have been disabled!"));
    }
}
