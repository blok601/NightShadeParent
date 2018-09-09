package com.nightshadepvp.core.cmd.cmds.staff;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Bukkit;

/**
 * Created by Blok on 9/9/2018.
 */
public class CmdBroadcast extends NightShadeCoreCommand {

    private static CmdBroadcast i = new CmdBroadcast();
    public static CmdBroadcast get() {return i;}

    public CmdBroadcast() {
        this.addAliases("broadcast", "bc", "bcast");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));
        this.addParameter(TypeString.get());
    }

    @Override
    public void perform() throws MassiveException {
        String message = this.readArg();
        Bukkit.broadcastMessage(ChatUtils.message(message));
    }
}
