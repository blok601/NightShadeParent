package com.nightshadepvp.core.cmd.cmds.admin;

import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.cmd.type.TypeRank;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.entity.Player;

public class CmdSetRank extends NightShadeCoreCommand {

    private static CmdSetRank i = new CmdSetRank();
    public static CmdSetRank get() { return i; }

    public CmdSetRank() {

        this.setAliases("setrank");

        this.addParameter(TypeNSPlayer.get(), "player");
        this.addParameter(TypeRank.get(), "rank");

        this.addRequirements(ReqRankHasAtLeast.get(Rank.ADMIN));
    }

    @Override
    public void perform() throws MassiveException {

        NSPlayer user = this.readArg();
        Rank rank = this.readArg();

        if ((sender instanceof Player) && !NSPlayer.get(sender).hasRank(rank)) {
            sender.sendMessage(ChatUtils.message("&cYou can't change that player's rank!"));
            return;
        }

        user.updateRank(rank);
        sender.sendMessage(ChatUtils.message("&aRank has been updated for &e" + user.getName() + " &ato " + rank.getPrefix()));

        if (user.isOnline()) {
            user.msg(ChatUtils.message("&aYour rank has been updated to "+ rank.getPrefix()));
        }

    }
}
