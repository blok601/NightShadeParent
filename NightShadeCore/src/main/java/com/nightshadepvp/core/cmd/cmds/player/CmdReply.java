package com.nightshadepvp.core.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.events.PlayerPrivateMessageEvent;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Bukkit;

/**
 * Created by Blok on 7/10/2018.
 */
public class CmdReply extends NightShadeCoreCommand{

    private static CmdReply i = new CmdReply();
    public static CmdReply get() {return i;}

    public CmdReply() {
        this.addAliases("reply", "r", "respond");
        this.addParameter(TypeString.get(), "message", true);
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        if(nsPlayer.getLastMessaged() == null){
            sender.sendMessage(ChatUtils.message("&cYou don't have anyone to respond to!"));
            return;
        }

        NSPlayer target = NSPlayer.get(nsPlayer.getLastMessaged());
        if(!target.isOnline()){
            nsPlayer.msg(ChatUtils.message("&cThat player is not online!"));
            return;
        }


        if(!target.isReceivingPMs()){
            nsPlayer.getPlayer().sendMessage(ChatUtils.message("&cThat player isn't receiving PMs right now!"));
            return;
        }

        String message = this.readArg();
        PlayerPrivateMessageEvent event = new PlayerPrivateMessageEvent(nsPlayer.getPlayer(), target.getPlayer(), message);
        Bukkit.getPluginManager().callEvent(event);

        if(event.isCancelled()) return;

        nsPlayer.setLastMessaged(target);
        target.setLastMessaged(nsPlayer);

        nsPlayer.getPlayer().sendMessage(ChatUtils.format("&7(" + nsPlayer.getRank().getNameColor() + "me &7-> " + target.getRank().getNameColor() + target.getName() + "&7) &7" + message));
        target.getPlayer().sendMessage(ChatUtils.format("&7(" + nsPlayer.getRank().getNameColor() + nsPlayer.getRank().getNameColor() + nsPlayer.getName() + " &7-> " + target.getRank().getNameColor() + "me&7) &7" + message));

    }
}
