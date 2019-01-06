package com.nightshadepvp.core.cmd.cmds.admin;

import com.google.common.collect.Sets;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.predicate.Predicate;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.NSPlayerColl;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by Blok on 1/6/2019.
 */
public class CmdMigrateRanks extends NightShadeCoreCommand {

    private static CmdMigrateRanks i = new CmdMigrateRanks();

    public static CmdMigrateRanks get() {
        return i;
    }

    public CmdMigrateRanks() {
        this.addAliases("migrateranks");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.HEADADMIN));
        this.setVisibility(Visibility.SECRET);
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);

        nsPlayer.msg(ChatUtils.message("&eThis is a heavy operation...please give it some time!"));
        HashMap<Rank, HashSet<Rank>> possible = new HashMap<>();
        possible.put(Rank.TRIAL, Sets.newHashSet(Rank.TRIAL, Rank.TRIALHOST));
        possible.put(Rank.STAFF, Sets.newHashSet(Rank.HOST, Rank.MOD));
        possible.put(Rank.SENIOR, Sets.newHashSet(Rank.SRHOST, Rank.SRMOD));

        HashMap<NSPlayer, Rank> updates = new HashMap<>();

        new BukkitRunnable() {
            @Override
            public void run() {
                Predicate<NSPlayer> where = nsPlayer1 -> nsPlayer1.hasRank(Rank.TRIAL);
                for (NSPlayer target : NSPlayerColl.get().getAll(where)) {
                    for (Map.Entry<Rank, HashSet<Rank>> entry : possible.entrySet()) {
                        if (entry.getValue().contains(target.getRank())) {
                            target.setRank(entry.getKey());
                            updates.put(target, target.getRank());
                        }
                    }
                }
            }
        }.runTaskAsynchronously(Core.get());

        nsPlayer.msg(ChatUtils.message("&eRank Updates:"));
        for (Map.Entry<NSPlayer, Rank> entry : updates.entrySet()) {
            nsPlayer.msg("&f- &b" + entry.getKey().getName() + " -> " + entry.getValue().getPrefix());
        }

    }
}
