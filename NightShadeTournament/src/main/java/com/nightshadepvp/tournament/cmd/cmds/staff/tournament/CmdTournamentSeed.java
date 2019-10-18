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
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
                GameHandler.getInstance().assignSeeds();
                p.sendMessage(ChatUtils.message("&eSeeds have been set!"));
            }
        }.runTaskLater(Tournament.get(), 20*5);
    }
}
