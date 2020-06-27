package com.nightshadepvp.tournament.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.tournament.Tournament;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.utils.PagedInventory;
import org.bukkit.entity.Player;

public class CmdHOF extends NightShadeTournamentCommand {

    private static CmdHOF i = new CmdHOF();
    public static CmdHOF get () {return i;}

    public CmdHOF() {
        this.addAliases("halloffame", "hof");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        Player player = (Player) sender;
        new PagedInventory(Tournament.get().getHofInventoryItems(), "&bHall of Fame", player);
    }
}
