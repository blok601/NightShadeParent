package com.nightshadepvp.core.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

public class CmdRefer extends NightShadeCoreCommand {

    private static CmdRefer i = new CmdRefer();
    public static CmdRefer get() {return i;}

    public CmdRefer() {
        this.addAliases("refer");
        this.addParameter(TypeNSPlayer.get(), "player", "The player that referred you");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer player = NSPlayer.get(sender);
        NSPlayer target = this.readArg();

        if(player.isHasReferred()){
            player.msg(ChatUtils.message("&cOnly one player can refer you!"));
            return;
        }

        player.setHasReferred(true);
        target.alterCoins(100);
        if(target.isOnline()){
            target.msg(ChatUtils.format("&f&m&o---------------------------------"));
            target.msg(ChatUtils.format("&a+100 coins &bfor referring &f" + player.getName()));
            target.msg(ChatUtils.format("&f&m&o---------------------------------"));
        }
    }
}
