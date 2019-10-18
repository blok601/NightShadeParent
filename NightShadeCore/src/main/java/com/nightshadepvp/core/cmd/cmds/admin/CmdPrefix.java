package com.nightshadepvp.core.cmd.cmds.admin;

import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

public class CmdPrefix extends NightShadeCoreCommand {

    private static CmdPrefix i = new CmdPrefix();
    public static CmdPrefix get() {return i;}

    public CmdPrefix() {
        this.addAliases("prefix");
        this.addParameter(TypeNSPlayer.get(), "player", "you");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.ADMIN));
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        NSPlayer target = this.readArg(nsPlayer);

        if(target.getPrefix().equalsIgnoreCase("")){
            nsPlayer.msg(ChatUtils.message("&cSince &b" + target.getName() + "&c doesn't have a prefix, they use their rank's default prefix&8: " + target.getRank().getPrefix()));
            return;
        }

        nsPlayer.msg(ChatUtils.message("&b" + target.getName() + "'s &eprefix is&8: " + target.getPrefix()));
    }
}
