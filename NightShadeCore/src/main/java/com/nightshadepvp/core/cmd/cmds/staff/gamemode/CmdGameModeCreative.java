package com.nightshadepvp.core.cmd.cmds.staff.gamemode;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 8/12/2019.
 */
public class CmdGameModeCreative extends NightShadeCoreCommand {

    public CmdGameModeCreative() {
        this.addAliases("creative", "c");
        this.addRequirements(RequirementIsPlayer.get(), ReqRankHasAtLeast.get(Rank.Builder));
        this.addParameter(TypePlayer.get(), "player", "you");
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg((Player) sender);
        NSPlayer senderNS = NSPlayer.get(sender);
        player.getPlayer().setGameMode(GameMode.CREATIVE);
        senderNS.msg(ChatUtils.message("&bUpdated &f" + player.getName() + "'s &bgamemode to &fcreative"));
    }
}
