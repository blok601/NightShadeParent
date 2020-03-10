package com.nightshadepvp.core.cmd.cmds.player.friend;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

public class CmdFriendRemove extends NightShadeCoreCommand {

    public CmdFriendRemove() {
        this.addAliases("remove", "delete");
        this.addParameter(TypeNSPlayer.get(), "player");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer player = NSPlayer.get(sender);
        NSPlayer target = this.readArg();
        if(target == null){
            player.msg(ChatUtils.message("&cThat player couldn't be found!"));
            return;
        }

        if(player.getOutGoingFriendRequests().remove(target.getUuid())){
            player.msg(ChatUtils.message("&fFriend request to &b" + target.getName() + " &fcancelled."));
            return;
        }

        if(player.getFriend(target.getUuid()) == null){
            player.msg(ChatUtils.message("&cYou are not friends with &e" + target.getName() + "&c."));
            return;
        }

        player.removeFriend(target.getUuid());
        target.removeFriend(player.getUuid());
        player.msg(ChatUtils.message("&fYou and &b" + target.getName() + " &fare no longer friends."));
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Message");
        out.writeUTF(target.getName());
        out.writeUTF(ChatUtils.message("&fYou and &b" + player.getName() + " &fare no longer friends."));
        player.getPlayer().sendPluginMessage(Core.get(), "BungeeCord", out.toByteArray());
        return;
    }
}
