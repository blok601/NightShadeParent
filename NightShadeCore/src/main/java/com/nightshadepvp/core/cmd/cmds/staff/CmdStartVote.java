package com.nightshadepvp.core.cmd.cmds.staff;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.fanciful.FancyMessage;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.UUID;

/**
 * Created by Blok on 11/18/2018.
 */
public class CmdStartVote extends NightShadeCoreCommand {

    public static CmdStartVote i = new CmdStartVote();

    public static CmdStartVote get() {
        return i;
    }

    public CmdStartVote() {
        this.addAliases("startvote");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.HOST));
        this.addParameter(TypeString.get(), "Vote Message", true);
    }

    public int YES_VOTES = 0;
    public int NO_VOTES = 0;
    public boolean voteRunning = false;
    public HashSet<UUID> voted = new HashSet<>();

    @Override
    public void perform() throws MassiveException {
        String vote = this.readArg();
        FancyMessage yes = new FancyMessage("YES").color(ChatColor.GREEN).style(ChatColor.BOLD).command("yes");
        FancyMessage no = new FancyMessage("NO").color(ChatColor.RED).style(ChatColor.BOLD).command("no");
        Bukkit.broadcastMessage(ChatUtils.message("&eA new vote has begun!"));
        Bukkit.broadcastMessage(ChatUtils.format("&3Vote&8» &e" + vote + "&8[" + yes + "&e| " + no + "&8]"));
        voteRunning = true;
        YES_VOTES = 0;
        NO_VOTES = 0;
        voted.clear();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (YES_VOTES > NO_VOTES) {
                    Bukkit.broadcastMessage(ChatUtils.format("&3Vote&8» &aYes &ehas won with &b" + YES_VOTES + " &evotes!"));
                } else if (NO_VOTES > YES_VOTES) {
                    Bukkit.broadcastMessage(ChatUtils.format("&&3Vote&8» &cNo &ehas won with &b" + NO_VOTES + " &evotes!"));
                } else if (YES_VOTES == NO_VOTES) {
                    Bukkit.broadcastMessage(ChatUtils.format("&3Vote&8» &eYes and No tied with &b" + YES_VOTES + "&evotes!"));
                }

                YES_VOTES = 0;
                NO_VOTES = 0;
                voteRunning = false;
                voted.clear();
            }
        }.runTaskLater(Core.get(), 400);
    }
}
