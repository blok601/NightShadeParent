package com.nightshadepvp.tournament.cmd.cmds.admin.arena;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.handler.ArenaHandler;
import com.nightshadepvp.tournament.utils.ChatUtils;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 7/18/2018.
 */
public class CmdArenaTeleport extends NightShadeTournamentCommand {

    public CmdArenaTeleport() {
        this.addAliases("teleport", "tp", "tport");
        this.addParameter(TypeString.get(), "arenaname");
        this.addRequirements(RequirementIsPlayer.get(), ReqRankHasAtLeast.get(Rank.SENIOR));
    }

    @Override
    public void perform() throws MassiveException {
        Player p = TPlayer.get(sender).getPlayer();
        String name = this.readArg();
        if(!ArenaHandler.getInstance().isArena(name)){
            p.sendMessage(ChatUtils.message("&cThat is not an arena! Make sure you spelled the name correctly!"));
            return;
        }

        p.teleport(ArenaHandler.getInstance().getArena(name).getSpawnLocation1());
        p.sendMessage(ChatUtils.message("&bTeleported to arena: &f" + name));
    }
}
