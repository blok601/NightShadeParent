package com.nightshadepvp.core.cmd.cmds.player.friend;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.objects.Friend;
import com.nightshadepvp.core.utils.ChatUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CmdFriendAdd extends NightShadeCoreCommand {

    public CmdFriendAdd() {
        this.addAliases("add", "request");
        this.addParameter(TypeNSPlayer.get(), "player");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer target = this.readArg();
        NSPlayer player = NSPlayer.get(sender);
        if(target == null){
            player.msg(ChatUtils.message("&cThat player couldn't be found!"));
            return;
        }

        // /friend add Toastinq

        // check if they're already friends and or there is already an outgoing request

        if(player.getFriend(target.getUuid()) != null){
            player.msg(ChatUtils.message("&cYou are already friends with &e" + target.getName() + "&c."));
        }

        if(player.getOutGoingFriendRequests().contains(target.getUuid())){
            player.msg(ChatUtils.message("&cYou have already sent a friend request to &e" + target.getName() + "&c."));
            return;
        }

        // if the other player has an outgoing request to the sender, make them friends
        // if not, create a friend request

        if(target.getOutGoingFriendRequests().contains(player.getUuid())){
            //Make them friends
            Date date = new Date(System.currentTimeMillis());
            Friend targetFriend = new Friend(target.getUuid(), date);
            Friend playerFriend = new Friend(player.getUuid(), date);
            target.getFriends().add(playerFriend);
            player.getFriends().add(targetFriend);
            if(target.isOnline()){
                target.msg(ChatUtils.message("&fYou and &b" + player.getName() + " &fare now friends."));
            }
            player.msg(ChatUtils.message("&fYou and &b" + target.getName() + " &fare now friends."));
            return;
        }

        //create a friend request
        player.getOutGoingFriendRequests().add(target.getUuid());
        player.msg(ChatUtils.message("&fFriend request sent to &b" + target.getName() + "&f."));
    }
}
