package com.nightshadepvp.core.cmd.cmds.admin;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsntPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.ServerType;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.entity.MConf;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

public class CmdSetServerType extends NightShadeCoreCommand {

    private static CmdSetServerType i = new CmdSetServerType();
    public static CmdSetServerType get() {return i;}

    public CmdSetServerType() {
        this.addAliases("setservertype");
        this.addParameter(TypeString.get(), "type");
        this.addRequirements(RequirementIsntPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        String s = this.readArg();
        if(s.toLowerCase().equals("tourney")){
            MConf.get().setServerType(ServerType.TOURNAMENT);
        }else if(s.toLowerCase().equals("uhc")){
            MConf.get().setServerType(ServerType.UHC);
        }else{
            NSPlayer.get(sender).msg(ChatUtils.message("&cInvalid arg!"));
        }
    }
}
