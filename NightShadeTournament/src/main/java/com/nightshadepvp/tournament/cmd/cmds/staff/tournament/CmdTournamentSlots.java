package com.nightshadepvp.tournament.cmd.cmds.staff.tournament;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeDouble;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.handler.GameHandler;
import com.nightshadepvp.tournament.utils.ChatUtils;
import com.nightshadepvp.tournament.utils.NumberUtils;

public class CmdTournamentSlots extends NightShadeTournamentCommand {

    public CmdTournamentSlots() {
        this.addAliases("slots");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));
        this.addParameter(TypeDouble.get(), "amount");
    }

    @Override
    public void perform() throws MassiveException {
        if (senderIsConsole) {
            if (this.argIsSet()) {
                double slots = this.readArg();

                if (slots > 128) {
                    return;
                }

                if (!NumberUtils.isBase2Number(slots)) {
                    return;
                }

                GameHandler.getInstance().setSlots((int) slots);
                return;
            }
        } else {
            TPlayer tPlayer = TPlayer.get(sender);
            double slots = this.readArg();

            if (slots > 128) {
                tPlayer.msg(ChatUtils.message("&cPlease supply a valid slot amount. Possible amounts are 2, 4, 8, 16, 32, 64, and 128"));
                return;
            }

            if (!NumberUtils.isBase2Number(slots)) {
                tPlayer.msg(ChatUtils.message("&cPlease supply a valid slot amount. Possible amounts are 2, 4, 8, 16, 32, 64, and 128"));
                return;
            }

            GameHandler.getInstance().setSlots((int) slots);
            tPlayer.msg(ChatUtils.message("&bYou have set the slots to &f" + (int) slots));
            return;
        }
    }
}
