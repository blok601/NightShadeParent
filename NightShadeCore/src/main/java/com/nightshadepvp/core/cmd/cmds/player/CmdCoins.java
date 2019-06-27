package com.nightshadepvp.core.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

/**
 * Created by Blok on 6/27/2019.
 */
public class CmdCoins extends NightShadeCoreCommand {

    private static CmdCoins i = new CmdCoins();

    public static CmdCoins get() {
        return i;
    }

    public CmdCoins() {
        this.addAliases("coins", "bal", "balance");
        this.addRequirements(RequirementIsPlayer.get());
        this.addParameter(TypeNSPlayer.get(), "player", "you");
        this.setDesc("View your current balance");
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        NSPlayer target = this.readArg(NSPlayer.get(sender));
        if(target == null){
            nsPlayer.msg(ChatUtils.message("&cInvalid player! Make sure they're name is spelled correctly."));
            return;
        }
        nsPlayer.msg(ChatUtils.format("&5&m--------------------------------------"));
        nsPlayer.msg(ChatUtils.message("&e" + target.getName() + "'s Balance: &b" + nsPlayer.getCoins() + " &ecoins"));
        nsPlayer.msg(ChatUtils.format("&5&m--------------------------------------"));
    }
}
