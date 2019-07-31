package com.nightshadepvp.tournament.cmd.cmds.admin.kits;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.handler.KitHandler;
import com.nightshadepvp.tournament.entity.objects.data.Kit;
import com.nightshadepvp.tournament.utils.ChatUtils;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 7/18/2018.
 */
public class CmdKitList extends NightShadeTournamentCommand {

    public CmdKitList() {
        this.addAliases("list");
        this.addRequirements(RequirementIsPlayer.get(), ReqRankHasAtLeast.get(Rank.TRIAL));
    }

    @Override
    public void perform() throws MassiveException {
        Player p = TPlayer.get(sender).getPlayer();
        p.sendMessage(ChatUtils.message("&eKits:"));
        p.sendMessage(ChatUtils.format("&3&m-------------------"));
        for (Kit k : KitHandler.getInstance().getKits()){
            p.sendMessage(ChatUtils.format("&e- " + k.getName()));
        }
        p.sendMessage(ChatUtils.format("&3&m-------------------"));
    }
}
