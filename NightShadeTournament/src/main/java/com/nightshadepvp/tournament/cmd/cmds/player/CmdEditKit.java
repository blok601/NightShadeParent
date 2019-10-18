package com.nightshadepvp.tournament.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.engine.EngineInventory;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.enums.PlayerStatus;
import com.nightshadepvp.tournament.entity.handler.MatchHandler;
import com.nightshadepvp.tournament.gui.KitGUI;
import com.nightshadepvp.tournament.utils.ChatUtils;

/**
 * Created by Blok on 8/5/2018.
 */
public class CmdEditKit extends NightShadeTournamentCommand {

    private static CmdEditKit i = new CmdEditKit();

    public static CmdEditKit get() {
        return i;
    }

    public CmdEditKit() {
        this.addAliases("editkit");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        TPlayer tPlayer = TPlayer.get(sender);

        if(tPlayer.getStatus() != PlayerStatus.LOBBY){
            tPlayer.msg(ChatUtils.message("&cYou must be in the lobby to edit kits!"));
            return;
        }

        if(MatchHandler.getInstance().getActiveMatch(tPlayer) != null){
            tPlayer.msg(ChatUtils.message("&cYou must be in the lobby to edit kits!"));
            return;
        }

        EngineInventory.edtingMap.put(tPlayer.getUuid(), null);
        new KitGUI(tPlayer.getPlayer());

    }
}
