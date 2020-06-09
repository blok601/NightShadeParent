package com.nightshadepvp.tournament.cmd.cmds.admin.kits;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.handler.KitHandler;
import com.nightshadepvp.tournament.entity.objects.data.Kit;
import com.nightshadepvp.tournament.utils.ChatUtils;
import com.nightshadepvp.tournament.utils.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 8/9/2018.
 */
public class CmdSetDisplay extends NightShadeTournamentCommand {

    public CmdSetDisplay() {
        this.addAliases("setdisplay");
        this.addParameter(TypeString.get(), "kitname");
        this.addRequirements(RequirementIsPlayer.get(), ReqRankHasAtLeast.get(Rank.ADMIN));
    }

    @Override
    public void perform() throws MassiveException {
        String name = this.readArg();
        Player p = (Player) sender;
        if (!KitHandler.getInstance().isKit(name)) {
            p.sendMessage(ChatUtils.message("&cThat kit does not exist! Make sure its spelled correctly!"));
            return;
        }

        if (PlayerUtils.playerEmptyInventory(p)) {
            p.sendMessage(ChatUtils.message("&cYou can't have an empty inventory!"));
            return;
        }

        if(p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR){
            p.sendMessage(ChatUtils.message("&cYou must have a valid item in your hand to set the display!"));
            return;
        }

        Kit kit = KitHandler.getInstance().getKit(name);
        kit.setDisplay(p.getItemInHand());
        p.sendMessage(ChatUtils.message("&bSet the display item for&8: &f" + kit.getName()));
    }
}
