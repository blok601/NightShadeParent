package com.nightshadepvp.core.cmd.cmds.admin;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.MConf;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

public class CmdSetSpawn extends NightShadeCoreCommand {

    private static CmdSetSpawn i = new CmdSetSpawn();
    public static CmdSetSpawn get() {return i;}

    public CmdSetSpawn() {
        this.addAliases("setspawn");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.ADMIN), RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        MConf.get().setSpawnLocation(nsPlayer.getPlayer().getLocation());
        nsPlayer.msg(ChatUtils.message("&bSet the spawn location to &fyour position"));
    }
}
