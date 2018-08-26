package com.nightshadepvp.core.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;


public class CmdLinkDiscord extends NightShadeCoreCommand {

    private static CmdLinkDiscord i = new CmdLinkDiscord();

    public static CmdLinkDiscord get() {
        return i;
    }

    public CmdLinkDiscord() {
        this.addAliases("linkdiscord");
        this.addParameter(TypeString.get(), "code");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        String code = this.readArg();
        if(Core.get().getJedis().get(code) == null){
            nsPlayer.msg(ChatUtils.message("&cThat code couldn't be found! It may have expired!"));
            return;
        }

        nsPlayer.setDiscordID(Long.parseLong(Core.get().getJedis().get(code)));
        nsPlayer.msg(ChatUtils.message("&eSuccessfully synced your Discord!"));
    }
}
