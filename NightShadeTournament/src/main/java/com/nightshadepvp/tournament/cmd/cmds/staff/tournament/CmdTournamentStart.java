package com.nightshadepvp.tournament.cmd.cmds.staff.tournament;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.tournament.Tournament;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.enums.TournamentState;
import com.nightshadepvp.tournament.entity.handler.GameHandler;
import com.nightshadepvp.tournament.event.TournamentStartEvent;
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
        GameHandler gameHandler = GameHandler.getInstance();

        if(gameHandler.isRunning()){
            tPlayer.msg(ChatUtils.message("&cYou can't start a tournament right now!"));
            return;
        }

        tPlayer.msg(ChatUtils.message("&bStarting the tournament in &f10&b seconds..."));
        gameHandler.setTournamentState(TournamentState.STARTING);
        new BukkitRunnable() {
            @Override
            public void run() {
                gameHandler.setTournamentState(TournamentState.IN_PROGRESS);
                Tournament.get().getServer().getPluginManager().callEvent(new TournamentStartEvent());
                gameHandler.assignMatches();
                tPlayer.msg(ChatUtils.message("&bMatches have been assigned and started!"));
            }
        }.runTaskLater(Tournament.get(), 10 * 20);
    }
}
