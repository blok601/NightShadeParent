package com.nightshadepvp.core.cmd.cmds.admin;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import org.bukkit.Bukkit;

/**
 * Created by Blok on 7/6/2018.
 */
public class CmdBlacklist extends NightShadeCoreCommand{

    private static CmdBlacklist i = new CmdBlacklist();
    public static CmdBlacklist get() {return i;}

    public CmdBlacklist() {
        this.addAliases("blacklist");
        this.addParameter(TypeString.get(), "player");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.ADMIN));
    }

    @Override
    public void perform() throws MassiveException {
        String ign = this.readArg();

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "banip " + ign + " [Blacklisted] You have been blacklisted from the NightShadePvP Network");
    }
}
