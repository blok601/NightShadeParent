package com.nightshadepvp.core.cmd.cmds.player;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.NSPlayerColl;
import com.nightshadepvp.core.utils.ChatUtils;

import java.util.ArrayList;

/**
 * Created by Blok on 8/7/2018.
 */
public class CmdList extends NightShadeCoreCommand {

    private static CmdList i = new CmdList();

    public static CmdList get() {
        return i;
    }

    public CmdList() {
        this.addAliases("list");
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer player = NSPlayer.get(sender);
        ArrayList<String> donors = Lists.newArrayList();
        ArrayList<String> players = Lists.newArrayList();
        ArrayList<String> staff = Lists.newArrayList();
        ArrayList<String> admin = Lists.newArrayList();
        ArrayList<String> media = Lists.newArrayList();

        for (NSPlayer nsPlayer : NSPlayerColl.get().getAllPlayersOnline()) {
            if (nsPlayer.getRank() == Rank.PLAYER || nsPlayer.getRank() == Rank.FRIEND) {
                players.add(nsPlayer.getRank().getNameColor() + nsPlayer.getName());
                continue;
            }

            if (nsPlayer.getRank().isDonorRank()) {
                donors.add(nsPlayer.getRank().getNameColor() + nsPlayer.getName());
                continue;
            }

            if (nsPlayer.getRank() == Rank.YOUTUBE) {
                media.add(nsPlayer.getRank().getNameColor() + nsPlayer.getName());
                continue;
            }

            if (nsPlayer.getRank() == Rank.TRIAL || nsPlayer.getRank() == Rank.STAFF || nsPlayer.getRank() == Rank.SENIOR) {
                staff.add(nsPlayer.getRank().getNameColor() + nsPlayer.getName());
                continue;
            }

            if (nsPlayer.getRank().isAdmin()) {
                admin.add(nsPlayer.getRank().getNameColor() + nsPlayer.getName());
                continue;
            }

            players.add(nsPlayer.getRank().getNameColor() + nsPlayer.getName());
        }

        ChatUtils.sortCollections(donors, players, staff, admin, media);

        player.msg(ChatUtils.format("&f&m--------------------------------------------"));
        player.msg(ChatUtils.format("&5Players &7(&b" + players.size() + "&7)&f: " + Joiner.on("&7,").join(players)));
        player.msg(ChatUtils.format("&5Donors &7(&b" + donors.size() + "&7)&f: " + Joiner.on("&7,").join(donors)));
        player.msg(ChatUtils.format("&5Media &7(&b" + media.size() + "&7)&f: " + Joiner.on("&7,").join(media)));
        player.msg(ChatUtils.format("&5Staff &7(&b" + staff.size() + "&7)&f: " + Joiner.on("&7,").join(staff)));
        player.msg(ChatUtils.format("&5Admins &7(&b" + admin.size() + "&7)&f: " + Joiner.on("&7,").join(admin)));
        player.msg(ChatUtils.format("&f&m--------------------------------------------"));

    }
}
