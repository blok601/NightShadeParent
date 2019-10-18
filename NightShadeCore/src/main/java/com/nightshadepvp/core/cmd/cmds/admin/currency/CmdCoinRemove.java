package com.nightshadepvp.core.cmd.cmds.admin.currency;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

/**
 * Created by Blok on 6/27/2019.
 */
public class CmdCoinRemove extends NightShadeCoreCommand {

    public CmdCoinRemove() {
        this.addRequirements(ReqRankHasAtLeast.get(Rank.ADMIN));
        this.setAliases("remove");
        this.setDesc("Remove coins from a player's balance");
        this.addParameter(TypeNSPlayer.get(), "player");
        this.addParameter(TypeInteger.get(), "amount");
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        NSPlayer target = this.readArg();
        int change = this.readArg();
        if (target == null) {
            nsPlayer.msg(ChatUtils.message("&cInvalid player! Make sure they're name is spelled correctly."));
            return;
        }

        if (target.getCoins() < change) {
            nsPlayer.msg(ChatUtils.message("&cA player's balance can not be set to less than 0!"));
            nsPlayer.msg(ChatUtils.message("&cTo reset &b" + target.getName() + "'s balance &edo /coin set " + target.getName() + " 0"));
            return;
        }

        target.alterCoins(-change);
        nsPlayer.msg(ChatUtils.format("&5&m--------------------------------------"));
        nsPlayer.msg(ChatUtils.message("&eYou have removed &b" + change + " coins &efrom &b" + target.getName() + "'s &ebalance"));
        nsPlayer.msg(ChatUtils.message("&b" + target.getName() + "'s &enew balance: &b" + target.getCoins() + " coins"));
        nsPlayer.msg(ChatUtils.format("&5&m--------------------------------------"));

        if (target.isOnline()) {
            target.msg(ChatUtils.format("&5&m--------------------------------------"));
            target.msg(ChatUtils.message("&b" + nsPlayer.getName() + " &ehas removed &b" + change + " coins &efrom your balance."));
            target.msg(ChatUtils.format("&eYou new balance: &b" + target.getCoins() + " coins"));
            target.msg(ChatUtils.format("&5&m--------------------------------------"));
        }
    }
}
