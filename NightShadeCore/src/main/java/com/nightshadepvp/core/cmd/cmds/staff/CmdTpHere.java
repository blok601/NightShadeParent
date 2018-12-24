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
public class CmdTpHere extends NightShadeCoreCommand{

    private static CmdTpHere i = new CmdTpHere();
    public static CmdTpHere get() {return i;}


    public CmdTpHere() {
        this.addAliases("tphere", "teleporthere");
        this.addParameter(TypeNSPlayer.get(), "player");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer targetPlayer = this.readArg();
        Player p = (Player) sender;

        if(!targetPlayer.isOnline()){
            p.sendMessage(ChatUtils.message("&cThat player is not online!"));
            return;
        }

        targetPlayer.getPlayer().teleport(p);
        p.sendMessage(ChatUtils.message("&eYou teleported &a" + targetPlayer.getName() + " &eto yourself!"));
        targetPlayer.msg(ChatUtils.message("&eYou were teleported to &a" + targetPlayer.getName()));
    }
}
