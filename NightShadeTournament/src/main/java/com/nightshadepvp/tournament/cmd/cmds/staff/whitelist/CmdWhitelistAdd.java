package com.nightshadepvp.tournament.cmd.cmds.staff.whitelist;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.handler.GameHandler;
import com.nightshadepvp.tournament.utils.ChatUtils;
import org.bukkit.Bukkit;

/**
 * Created by Blok on 8/8/2018.
 */
public class CmdWhitelistAdd extends NightShadeTournamentCommand {

    public CmdWhitelistAdd() {
         this.addAliases("add");
         this.addParameter(TypeString.get(), "player/*");
         this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));
    }

    @Override
    public void perform() throws MassiveException {
        String typeString = this.readArg();
        TPlayer tPlayer = TPlayer.get(sender);
        if(typeString.equalsIgnoreCase("*") || typeString.equalsIgnoreCase("all")){
            Bukkit.getOnlinePlayers().stream().filter(o -> !GameHandler.getInstance().getWhitelist().contains(o.getName())).forEach(o -> GameHandler.getInstance().getWhitelist().add(o.getName()));
            tPlayer.msg(ChatUtils.message("&eAdded everyone to the whitelist!"));
            return;
        }

        if(GameHandler.getInstance().getWhitelist().contains(typeString.toLowerCase())){
            tPlayer.msg(ChatUtils.message("&eThat player is already on the whitelist!"));
            return;
        }

        GameHandler.getInstance().getWhitelist().add(typeString.toLowerCase());
        tPlayer.msg(ChatUtils.message("&eAdded &3" + typeString + " &eto the whitelist!"));
    }
}
