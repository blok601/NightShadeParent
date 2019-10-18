package com.nightshadepvp.tournament.cmd.cmds.admin.kits;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.handler.KitHandler;
import com.nightshadepvp.tournament.entity.objects.data.Kit;
import com.nightshadepvp.tournament.utils.ChatUtils;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 7/18/2018.
 */
public class CmdKitRemove extends NightShadeTournamentCommand {

    public CmdKitRemove(){
        this.addAliases("remove", "delete");
        this.addRequirements(RequirementIsPlayer.get());
        this.addRequirements(ReqRankHasAtLeast.get(Rank.ADMIN));
        this.setVisibility(Visibility.SECRET);
        this.addParameter(TypeString.get(), "kitname");
    }

    @Override
    public void perform() throws MassiveException {
        Player p = TPlayer.get(sender).getPlayer();
        String name = this.readArg();
        if(!KitHandler.getInstance().isKit(name)){
            p.sendMessage(ChatUtils.message("&cThat kit does not exist! Make sure its spelled correctly!"));
            return;
        }

        Kit kit = KitHandler.getInstance().getKit(name);
        KitHandler.getInstance().removeKit(kit);
        p.sendMessage(ChatUtils.message("&eSuccessfully removed kit: " + kit.getName()));
        p.sendMessage(ChatUtils.message("&eMake sure to reload kits!"));
    }
}
