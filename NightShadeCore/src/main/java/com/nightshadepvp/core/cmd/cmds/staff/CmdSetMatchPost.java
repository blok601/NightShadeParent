package com.nightshadepvp.core.cmd.cmds.staff;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.events.MatchpostUpdateEvent;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Bukkit;

/**
 * Created by Blok on 9/9/2018.
 */
public class CmdSetMatchPost extends NightShadeCoreCommand {

    private static CmdSetMatchPost i = new CmdSetMatchPost();

    public static CmdSetMatchPost get() {
        return i;
    }

    public CmdSetMatchPost() {
        this.addAliases("setmatchpost", "setpost");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));
        this.addParameter(TypeString.get());
    }


    @Override
    public void perform() throws MassiveException {
        String post = this.readArg();
        NSPlayer nsPlayer = NSPlayer.get(sender);
        Core.get().setMatchpost(post);
        Bukkit.getPluginManager().callEvent(new MatchpostUpdateEvent(post));
        nsPlayer.msg(ChatUtils.message("&eYou have set the matchpost to&8: &3" + post));
    }
}
