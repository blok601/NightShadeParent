package com.nightshadepvp.core.cmd.cmds.admin.login;

import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayerColl;

/**
 * Created by Blok on 6/20/2019.
 */
public class CmdUpdateAllPassword extends NightShadeCoreCommand {

    private static CmdUpdateAllPassword i = new CmdUpdateAllPassword();

    public static CmdUpdateAllPassword get() {
        return i;
    }

    public CmdUpdateAllPassword() {
        this.setAliases("updateallpassword");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.OWNER));
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayerColl.get().getAll().forEach(nsPlayer -> nsPlayer.setAdminPassword(null));
        sender.sendMessage("Done!");
    }
}
