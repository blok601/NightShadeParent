package com.nightshadepvp.tournament.cmd.cmds.staff.tournament;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.tournament.Tournament;
import com.nightshadepvp.tournament.challonge.Challonge;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.handler.GameHandler;
import com.nightshadepvp.tournament.entity.handler.MatchHandler;
import com.nightshadepvp.tournament.entity.handler.RoundHandler;
import com.nightshadepvp.tournament.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ExecutionException;

/**
 * Created by Blok on 7/18/2018.
 */
public class CmdTournamentSeed extends NightShadeTournamentCommand {

    public CmdTournamentSeed() {
        this.addAliases("seed", "assignseeds");
        this.addRequirements(RequirementIsPlayer.get(), ReqRankHasAtLeast.get(Rank.TRIAL));
    }

    @Override
    public void perform() throws MassiveException {
        Player p = TPlayer.get(sender).getPlayer();
        p.sendMessage(ChatUtils.message("&eSeeding players in 10 seconds...Make sure all spectators are in spectator mode to avoid a mis-seed!"));
        new BukkitRunnable(){
            @Override
            public void run() {
                Challonge challonge = Tournament.get().getChallonge();
                if (challonge == null) {
                    p.sendMessage(ChatUtils.message("&cThe tournament has not been posted yet!"));
                    cancel();
                    return;
                }


                try {
                    GameHandler.getInstance().assignSeeds();
                    challonge.addParticpants().get();
                    challonge.indexMatches().get();


                    MatchHandler.getInstance().setupChallonge();
                    RoundHandler.getInstance().setupChallonge();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    p.sendMessage(ChatUtils.message("&cThere was a problem assigning seeds... contact an admin if the problem persists!"));
                    return;
                }

                p.sendMessage(ChatUtils.message("&bSeeds have been set and participants have been added to the bracket!"));
            }
        }.runTaskLater(Tournament.get(), 20*5);
    }
}
