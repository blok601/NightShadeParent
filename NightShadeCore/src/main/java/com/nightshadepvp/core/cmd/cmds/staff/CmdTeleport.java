package com.nightshadepvp.core.cmd.cmds.staff;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 7/6/2018.
 */
public class CmdTeleport extends NightShadeCoreCommand{

    private static CmdTeleport i = new CmdTeleport();
    public static CmdTeleport get() {return i;}


    public CmdTeleport() {
        this.addAliases("teleport", "tp", "tport");
        this.addParameter(TypeNSPlayer.get(), "player");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer target = this.readArg();
        Player p = (Player) sender;
        if(!target.isOnline()){
            p.sendMessage(ChatUtils.message("&cThat player is offline!"));
            return;
        }

        p.teleport(target.getPlayer().getLocation());

        p.sendMessage(ChatUtils.message("&eTeleported to &a" + target.getName()));
    }
}
