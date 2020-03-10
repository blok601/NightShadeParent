package com.nightshadepvp.core.cmd.cmds.player.friend;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.gui.guis.FriendsGUI;
import com.nightshadepvp.core.utils.ChatUtils;

public class CmdFriends extends NightShadeCoreCommand {

    private static CmdFriends i = new CmdFriends();
    public static CmdFriends get() {return i;}

    public CmdFriends() {
        this.addAliases("friends");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer player = NSPlayer.get(sender);
        if(player.getFriends().size() == 0){
            player.msg(ChatUtils.message("&cThere is nobody on your friends list!"));
            return;
        }

        player.msg(ChatUtils.message("&f&oOpening friends list..."));
        new FriendsGUI(player);
    }
}
