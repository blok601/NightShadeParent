package com.nightshadepvp.core.cmd.cmds.admin;

import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.cmd.type.TypePlayerTag;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.objects.PlayerTag;
import com.nightshadepvp.core.utils.ChatUtils;

public class CmdAddTag extends NightShadeCoreCommand {

    private static CmdAddTag i = new CmdAddTag();
    public static CmdAddTag get() { return i; }

    public CmdAddTag() {

        this.setAliases("addtag");

        this.addParameter(TypeNSPlayer.get(), "player");
        this.addParameter(TypePlayerTag.get(), "tag");

        this.addRequirements(ReqRankHasAtLeast.get(Rank.ADMIN));
    }

    @Override
    public void perform() throws MassiveException {

        NSPlayer user = this.readArg();
        PlayerTag tag = this.readArg();
        NSPlayer senderNSPlayer = NSPlayer.get(sender);

        if (user.getUnlockedTags().contains(tag)) {
            user.getUnlockedTags().remove(tag);
        } else {
            user.getUnlockedTags().add(tag);
        }
        senderNSPlayer.msg(ChatUtils.message("&aAdded the tag: " + tag.getFormatted()));
        if (user.isOnline()) {
            user.msg(ChatUtils.message("&eYou have been granted the " + tag.getFormatted() + " &etag!"));
        }

    }
}
