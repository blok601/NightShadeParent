package com.nightshadepvp.core.cmd.cmds.staff;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

/**
 * Created by Blok on 7/6/2018.
 */
public class CmdStaffChat extends NightShadeCoreCommand{

    private static CmdStaffChat i = new CmdStaffChat();
    public static CmdStaffChat get() {return i;}

    public CmdStaffChat() {
        this.addAliases("sc", "staffchat");
        this.addParameter(TypeString.get(), "message", "none", true);
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer user = NSPlayer.get(sender);
        String message = this.readArg("null");

        if (message.equals("null")) { // No Message (aka param)
            if(user.isInStaffChat()){
                user.setInStaffChat(false);
                user.getPlayer().sendMessage(ChatUtils.message("&eYou are no longer in staff chat!"));
                return;
            }

            user.setInStaffChat(true);
            user.getPlayer().sendMessage(ChatUtils.message("&eYou have entered staff chat"));
            return;
        } else { // They gave a param)
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF(user.getName());
            out.writeUTF(message.trim());
            user.getPlayer().sendPluginMessage(Core.get(), "staffchat", out.toByteArray());
            Core.get().getLogManager().log(Logger.LogType.DEBUG, "Staff Chat line 61");
        }

    }
}
