package com.nightshadepvp.core.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.objects.PlayerColor;
import com.nightshadepvp.core.utils.ChatUtils;

/**
 * Created by Blok on 7/6/2018.
 */
public class CmdColors extends NightShadeCoreCommand{

    private static CmdColors i = new CmdColors();
    public static CmdColors get() {return i;}


    public CmdColors() {
        this.addAliases("colors", "viewcolors");
        this.addParameter(TypeNSPlayer.get(), "player", "you");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer user = this.readArg(NSPlayer.get(sender));

        StringBuilder builder = new StringBuilder();
        for (PlayerColor color : user.getColors()){
            builder.append(color.toString()).append(",");
        }

        String colors = builder.toString().trim().substring(0, builder.toString().trim().length());

        if(user.getName().equalsIgnoreCase(sender.getName())){
            user.msg(ChatUtils.message("&eYou have the following colors&8: &a" + colors));
            user.msg(ChatUtils.message("&eYour selected color&8: &a" + user.getColor()));
        }else{
            NSPlayer.get(sender).msg(ChatUtils.message("&a" + user.getName() + " &ehas the following colors&8: &a" + colors));
            NSPlayer.get(sender).msg(ChatUtils.message("&a" + user.getName() + "'s &eselected color&8: &a" + user.getColor()));
        }

    }
}
