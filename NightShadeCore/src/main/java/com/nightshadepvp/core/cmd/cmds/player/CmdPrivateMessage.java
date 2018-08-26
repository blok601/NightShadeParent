package com.nightshadepvp.core.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.events.PlayerPrivateMessageEvent;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Bukkit;

/**
 * Created by Blok on 7/6/2018.
 */
public class CmdPrivateMessage extends NightShadeCoreCommand{

    private static CmdPrivateMessage i = new CmdPrivateMessage();
    public static CmdPrivateMessage get() {return i;}

    public CmdPrivateMessage() {
        this.addAliases("message", "tell", "whistper", "dm", "t", "w", "m", "msg");
        this.addParameter(TypeNSPlayer.get(), "player");
        this.addParameter(TypeString.get(), "message", true);
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer targetUser = this.readArg();
        String message = this.readArg();
        NSPlayer sendingPlayer = NSPlayer.get(sender);


        if(!targetUser.isOnline()){
            sendingPlayer.msg(ChatUtils.message("&cThat player is not online!"));
            return;
        }


        if(!targetUser.isReceivingPMs()){
            sendingPlayer.getPlayer().sendMessage(ChatUtils.message("&cThat player isn't receiving PMs right now!"));
            return;
        }

        PlayerPrivateMessageEvent event = new PlayerPrivateMessageEvent(sendingPlayer.getPlayer(), targetUser.getPlayer(), message);
        Bukkit.getPluginManager().callEvent(event);

        if(event.isCancelled()) return;

        sendingPlayer.setLastMessaged(targetUser);
        targetUser.setLastMessaged(sendingPlayer);

        sendingPlayer.getPlayer().sendMessage(ChatUtils.format("&7(" + sendingPlayer.getRank().getNameColor() + "me &7-> " + targetUser.getRank().getNameColor() + targetUser.getName() + "&7) &7" + message));
        targetUser.getPlayer().sendMessage(ChatUtils.format("&7(" + sendingPlayer.getRank().getNameColor() + sendingPlayer.getRank().getNameColor() + sendingPlayer.getName() + " &7-> " + targetUser.getRank().getNameColor() + "me&7) &7" + message));
    }
}
