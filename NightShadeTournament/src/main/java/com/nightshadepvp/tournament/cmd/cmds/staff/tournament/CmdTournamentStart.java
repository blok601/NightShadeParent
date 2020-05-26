package com.nightshadepvp.tournament.cmd.cmds.staff.tournament;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.tournament.Tournament;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.handler.GameHandler;
import com.nightshadepvp.tournament.utils.ChatUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ExecutionException;

/**
 * Created by Blok on 7/21/2018.
 */
public class CmdTournamentStart extends NightShadeTournamentCommand {

    public CmdTournamentStart() {
        this.addAliases("start");
        this.addRequirements(RequirementIsPlayer.get());
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));
    }

    @Override
    public void perform() throws MassiveException {
        TPlayer tPlayer = TPlayer.get(sender);

        tPlayer.msg(ChatUtils.message("&eStarting the tournament in 10 seconds..."));
        new BukkitRunnable(){
            @Override
            public void run() {
                try {
                    if (Tournament.get().getChallonge().start().get()) {
                        GameHandler.getInstance().assignMatches();
                        tPlayer.msg(ChatUtils.message("&eMatches have been assigned and started!"));
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskLater(Tournament.get(), 10*20);
    }
}
