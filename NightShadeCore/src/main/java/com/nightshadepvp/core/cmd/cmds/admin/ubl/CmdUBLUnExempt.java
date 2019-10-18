package com.nightshadepvp.core.cmd.cmds.admin.ubl;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.MConf;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

/**
 * Created by Blok on 12/17/2018.
 */
public class CmdUBLUnExempt extends NightShadeCoreCommand {

    public CmdUBLUnExempt() {
        this.addAliases("unexempt");
        this.addParameter(TypeString.get(), "player name");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.ADMIN));
        this.setVisibility(Visibility.SECRET);
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        String to = this.readArg();
        if (!MConf.get().getExempt().contains(to.toLowerCase())) {
            nsPlayer.msg(ChatUtils.message("&cThat player is not exempt!"));
            return;
        }

        MConf.get().getExempt().remove(to.toLowerCase());
        MConf.get().changed();
        nsPlayer.msg(ChatUtils.message("&e" + to + " is now unexmpt from the UBL!"));

    }
}
