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
public class CmdInvsee extends NightShadeCoreCommand{

    private static CmdInvsee i = new CmdInvsee();
    public static CmdInvsee get() {return i;}

    public CmdInvsee() {
        this.setAliases("invsee");
        this.addParameter(TypeNSPlayer.get(), "player");
        this.addRequirements(RequirementIsPlayer.get());
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));
    }

    @Override
    public void perform() throws MassiveException {
        Player p = (Player) sender;
        NSPlayer target = this.readArg();
        if(target.isOnline()){
            p.sendMessage(ChatUtils.message("&eOpening &a" + target.getName() + "'s &einventory..."));
            p.openInventory(target.getPlayer().getInventory());
        }
    }
}
