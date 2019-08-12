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
public class CmdGmC extends NightShadeCoreCommand {

    private static CmdGmC i = new CmdGmC();

    public static CmdGmC get() {
        return i;
    }

    public CmdGmC() {
        this.addAliases("gmc", "gamemodec");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.Builder), RequirementIsPlayer.get());
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
