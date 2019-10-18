package com.nightshadepvp.tournament.cmd.cmds.staff.tournament;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.handler.GameHandler;
import com.nightshadepvp.tournament.utils.ChatUtils;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 7/18/2018.
 */
public class CmdTournamentClaimHost extends NightShadeTournamentCommand {

    public CmdTournamentClaimHost() {
        this.addAliases("claimhost");
        this.addRequirements(RequirementIsPlayer.get(), ReqRankHasAtLeast.get(Rank.TRIAL));
    }

    @Override
    public void perform() throws MassiveException {
        Player p = TPlayer.get(sender).getPlayer();
        if(GameHandler.getInstance().getHost() != null && GameHandler.getInstance().getHost().getName().equalsIgnoreCase(p.getName())){
            p.sendMessage(ChatUtils.message("&cYou are already the host!"));
            return;
        }
        GameHandler.getInstance().setHost(p);
        p.sendMessage(ChatUtils.message("&eYou are now the host!"));
    }
}
