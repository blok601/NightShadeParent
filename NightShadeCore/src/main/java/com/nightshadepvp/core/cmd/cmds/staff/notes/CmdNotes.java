package com.nightshadepvp.core.cmd.cmds.staff.notes;

import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

public class CmdNotes extends NightShadeCoreCommand {

    private static CmdNotes i = new CmdNotes();
    public static CmdNotes get(){return i;}

    public CmdNotes() {
        this.addAliases("notes");
        this.addParameter(TypeNSPlayer.get(), "player");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        NSPlayer target = this.readArg();
        if(target == null){
            nsPlayer.msg(ChatUtils.message("&cThat player couldn't be found!"));
            return;
        }

        if(target.hasRank(Rank.TRIAL)){
            nsPlayer.msg(ChatUtils.message("&cYou can't manage notes of other staff members!"));
            return;
        }

        nsPlayer.msg(ChatUtils.message("&f" + target.getName() + "'s &bnotes:"));
        nsPlayer.msg(ChatUtils.format("&b&m----------------------------------"));
        int i = 1;
        for (String note : target.getNotes()){
            nsPlayer.msg(ChatUtils.format("&f" + i + ". " + note));
            i++;
        }
        nsPlayer.msg(ChatUtils.format("&b&m----------------------------------"));
    }
}
