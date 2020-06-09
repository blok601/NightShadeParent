package com.nightshadepvp.tournament.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.NSPlayerColl;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.utils.ChatUtils;

/**
 * Created by Blok on 8/8/2018.
 */
public class CmdHelpop extends NightShadeTournamentCommand {

    private static CmdHelpop i = new CmdHelpop();

    public static CmdHelpop get() {
        return i;
    }

    public CmdHelpop() {
        this.addAliases("helpop");
        this.addParameter(TypeString.get(), "message", true);
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        String helpopMessage = this.readArg();
        TPlayer senderT = TPlayer.get(sender);
        for (NSPlayer nsPlayer : NSPlayerColl.get().getAllOnline()) {
            if (nsPlayer.hasRank(Rank.TRIAL)) {
                nsPlayer.msg((ChatUtils.format("&8[&3HelpOP&8] &3" + senderT.getName() + "&8: &e" + helpopMessage)));
            }
        }
        senderT.msg(ChatUtils.message("&bYour message was sent to the moderators."));
    }

}
