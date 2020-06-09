package com.nightshadepvp.tournament.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.engine.EngineInventory;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.enums.PlayerStatus;
import com.nightshadepvp.tournament.entity.objects.data.Kit;
import com.nightshadepvp.tournament.utils.ChatUtils;
import com.nightshadepvp.tournament.utils.InventoryUtils;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 8/5/2018.
 */
public class CmdSaveKit extends NightShadeTournamentCommand {

    private static CmdSaveKit i = new CmdSaveKit();
    public static CmdSaveKit get() {return i;}

    public CmdSaveKit() {
        this.addAliases("savekit");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        Player p = (Player) sender;
        TPlayer tPlayer = TPlayer.get(p);

        if(tPlayer.getStatus() != PlayerStatus.EDITING){
            Core.get().getLogManager().log(Logger.LogType.DEBUG, tPlayer.getName() + "'s Status is " + tPlayer.getStatus().toString());
            p.sendMessage(ChatUtils.message("&cYou must be editing a kit to save your kit!"));
            Core.get().getLogManager().log(Logger.LogType.DEBUG, "Here - 36");
            return;
        }

        if(!EngineInventory.edtingMap.containsKey(p.getUniqueId())){
            p.sendMessage(ChatUtils.message("&cYou must be editing a kit to save your kit!"));
            Core.get().getLogManager().log(Logger.LogType.DEBUG, tPlayer.getName() + "'s Status is " + tPlayer.getStatus().toString());
            Core.get().getLogManager().log(Logger.LogType.DEBUG, "Here - 42");
            return;
        }

        Kit kit = EngineInventory.edtingMap.get(p.getUniqueId());
        if(kit == null){
            p.sendMessage(ChatUtils.message("&cThere was a problem saving the kit! Sending you to spawn!"));
            tPlayer.sendSpawn();
            EngineInventory.edtingMap.remove(p.getUniqueId());
            return;
        }

        tPlayer.getPlayerKits().put(kit, InventoryUtils.playerInventoryFromPlayer(p));
        EngineInventory.edtingMap.remove(p.getUniqueId());
        p.sendMessage(ChatUtils.message("&bSuccessfully saved kit &f" + kit.getName()));
        tPlayer.sendSpawn();
    }
}
