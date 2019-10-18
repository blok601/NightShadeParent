package com.nightshadepvp.tournament.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.handler.InventoryManager;
import com.nightshadepvp.tournament.entity.objects.player.ViewableInventory;
import com.nightshadepvp.tournament.utils.ChatUtils;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 8/5/2018.
 */
public class CmdViewPlayerInventory extends NightShadeTournamentCommand {

    private static CmdViewPlayerInventory i = new CmdViewPlayerInventory();
    public static CmdViewPlayerInventory get() {return i;}

    public CmdViewPlayerInventory() {
        this.addAliases("viewplayerinventory");
        this.addParameter(TypePlayer.get(), "player");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        Player target = this.readArg();
        TPlayer tPlayer = TPlayer.get(sender);
        if(target == null){
            tPlayer.msg(ChatUtils.message("&cThat player couldn't be found!"));
            return;
        }

        if(!InventoryManager.getInstance().hasViewableInventory(target)){
            tPlayer.msg(ChatUtils.message("&cThat player has logged off or their inventory expired!"));
            return;
        }

        ViewableInventory inv = InventoryManager.getInstance().getInventory(target);

        tPlayer.getPlayer().openInventory(inv.getInventory());

    }
}
