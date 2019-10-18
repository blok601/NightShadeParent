package com.nightshadepvp.core.cmd.req;

import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.command.requirement.RequirementAbstract;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReqRankHasAtLeast extends RequirementAbstract {

    private static final long serialVersionUID = 1L;

    private final Rank rank;
    public Rank getRank() {
        return this.rank;
    }

    public static ReqRankHasAtLeast get(Rank rank) {
        return new ReqRankHasAtLeast(rank);
    }
    private ReqRankHasAtLeast(Rank rank) { this.rank = rank;  }

    @Override
    public boolean apply(CommandSender sender, MassiveCommand command) {
        if (!(sender instanceof Player)) return true;
        return NSPlayer.get(sender).hasRank(this.rank);
    }

    @Override
    public String createErrorMessage(CommandSender sender, MassiveCommand command) {
        return ChatUtils.message("&cYou require the " + this.rank.getPrefix() + "&crank to do this command!");
    }
}