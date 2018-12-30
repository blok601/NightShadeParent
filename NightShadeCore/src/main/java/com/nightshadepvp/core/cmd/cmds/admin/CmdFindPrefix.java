package com.nightshadepvp.core.cmd.cmds.admin;

import com.google.common.base.Joiner;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.store.SenderEntity;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.NSPlayerColl;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.stream.Collectors;

public class CmdFindPrefix extends NightShadeCoreCommand {

    private static CmdFindPrefix i = new CmdFindPrefix();

    public static CmdFindPrefix get() {
        return i;
    }

    public CmdFindPrefix() {
        this.addAliases("findmalformedprefixes");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.OWNER));
        this.setVisibility(Visibility.SECRET);
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        nsPlayer.msg(ChatUtils.message("&eThis could take a while... be patient!"));
        new BukkitRunnable() {
            @Override
            public void run() {
                int i = 0;
                HashSet<NSPlayer> nsPlayers = new HashSet<>();
                for (NSPlayer target : NSPlayerColl.get().getAll()) {
                    if (!target.getPrefix().equalsIgnoreCase("")) {
                        if (target.getPrefix().trim().equalsIgnoreCase("&8[&c★&8]")) {
                            nsPlayers.add(target);
                            i++;
                            continue;
                        }

                        if (target.getPrefix().trim().equalsIgnoreCase("&8[&cWinner&8]")) {
                            nsPlayers.add(target);
                            i++;
                            continue;
                        }

                        if (target.getPrefix().trim().equalsIgnoreCase("&c[&cWinner&c]")) {
                            nsPlayers.add(target);
                            i++;
                        }
                    }
                }

                nsPlayer.msg(ChatUtils.message("&eThere are &b" + i + "&e players with prefixes to clear:"));
                nsPlayer.msg(ChatUtils.message("&b" + Joiner.on("&7, &b").join(nsPlayers.stream().map(SenderEntity::getName).collect(Collectors.toList()))));
            }
        }.runTaskAsynchronously(Core.get());
    }
}
