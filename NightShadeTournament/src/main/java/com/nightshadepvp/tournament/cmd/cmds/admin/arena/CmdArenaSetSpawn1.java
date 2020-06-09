package com.nightshadepvp.tournament.cmd.cmds.admin.arena;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.handler.ArenaHandler;
import com.nightshadepvp.tournament.entity.objects.data.Arena;
import com.nightshadepvp.tournament.utils.ChatUtils;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 7/18/2018.
 */
public class CmdArenaSetSpawn1 extends NightShadeTournamentCommand {

    public CmdArenaSetSpawn1() {
        this.addAliases("setspawn1");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.ADMIN), RequirementIsPlayer.get());
        this.addParameter(TypeString.get(), "arenaname");
    }

    @Override
    public void perform() throws MassiveException {
        Player p = TPlayer.get(sender).getPlayer();
        String name = this.readArg();
        if(!ArenaHandler.getInstance().isArena(name)){
            p.sendMessage(ChatUtils.message("&cThat is not an arena! Make sure you spelled the name correctly!"));
            return;
        }

        Arena arena = ArenaHandler.getInstance().getArena(name);
        if(arena.isInUse()){
            p.sendMessage(ChatUtils.message("&cYou can't do that while the arena is in use!"));
            return;
        }

        arena.setSpawnLocation1(p.getLocation());
        p.sendMessage(ChatUtils.message("&bSuccessfully set the first spawn location for arena: &f" + arena.getName()));
    }
}
