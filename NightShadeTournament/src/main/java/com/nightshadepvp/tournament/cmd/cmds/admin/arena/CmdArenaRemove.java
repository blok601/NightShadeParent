package com.nightshadepvp.tournament.cmd.cmds.admin.arena;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
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
public class CmdArenaRemove extends NightShadeTournamentCommand {

    public CmdArenaRemove(){
        this.addAliases("remove", "delete");
        this.addRequirements(RequirementIsPlayer.get());
        this.addRequirements(ReqRankHasAtLeast.get(Rank.ADMIN));
        this.setVisibility(Visibility.SECRET);
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

        ArenaHandler.getInstance().removeArena(arena);
        p.sendMessage(ChatUtils.message("&bSuccessfully removed arena: &f" + arena));
        return;
    }
}
