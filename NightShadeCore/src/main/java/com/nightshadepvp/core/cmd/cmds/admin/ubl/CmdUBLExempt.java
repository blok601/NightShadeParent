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

import java.util.ArrayList;

/**
 * Created by Blok on 12/17/2018.
 */
public class CmdUBLExempt extends NightShadeCoreCommand {

    public CmdUBLExempt() {
        this.addAliases("exempt", "pardon");
        this.addParameter(TypeString.get(), "player name");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.ADMIN));
        this.setVisibility(Visibility.SECRET);
    }


    @Override
    public void perform() throws MassiveException {
        String to = this.readArg();
        NSPlayer nsPlayer = NSPlayer.get(sender);
        ArrayList<String> ex = MConf.get().getExempt();
        if (ex.contains(to.toLowerCase())) {
            nsPlayer.msg(ChatUtils.message("&cThat player is already exempt!"));
            return;
        }

        ex.add(to.toLowerCase());
        MConf.get().changed();
        nsPlayer.msg(ChatUtils.message("&e" + to + " is now exempt from the UBL on this server"));
    }
}
