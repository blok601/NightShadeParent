package com.nightshadepvp.core.cmd.cmds.admin;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

/**
 * Created by Blok on 7/6/2018.
 */
public class CmdSetPrefix extends NightShadeCoreCommand{

    private static CmdSetPrefix i = new CmdSetPrefix();
    public static CmdSetPrefix get() {return i;}


    public CmdSetPrefix() {
        this.setAliases("setprefix");

        this.addParameter(TypeNSPlayer.get(), "player");
        this.addParameter(TypeString.get(), "prefix");

        this.addRequirements(ReqRankHasAtLeast.get(Rank.ADMIN));
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer user = this.readArg();
        String prefix = this.readArg();


        if(prefix.equalsIgnoreCase("none")){
            user.setPrefix("");
            sender.sendMessage(ChatUtils.message("&eYou have removed &a" + user.getName() + "'s &eprefix"));
            if(user.isOnline()){
                user.msg(ChatUtils.message("&eYour prefix has been removed!"));
            }
            return;
        }

        user.setPrefix(prefix);
        if(user.isOnline()){
            user.getPlayer().sendMessage(ChatUtils.message("&eYour prefix has been updated to " + prefix));
        }
        sender.sendMessage(ChatUtils.message("&eYou have updated &a" + user.getName() + "&e's prefix to &a"  + user.getPrefix()));
    }
}
