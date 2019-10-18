package com.nightshadepvp.core.cmd.cmds.admin;

import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.cmd.type.TypePlayerColor;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.objects.PlayerColor;
import com.nightshadepvp.core.utils.ChatUtils;

public class CmdAddColor extends NightShadeCoreCommand {

    private static CmdAddColor i = new CmdAddColor();
    public static CmdAddColor get() { return i; }

    public CmdAddColor() {

        this.setAliases("addcolor");

        this.addParameter(TypeNSPlayer.get(), "player");
        this.addParameter(TypePlayerColor.get(), "color");

        this.addRequirements(ReqRankHasAtLeast.get(Rank.SENIOR));
    }

    @Override
    public void perform() throws MassiveException {

        NSPlayer user = this.readArg();
        PlayerColor playerColor = this.readArg();

        if (user.getColors().contains(playerColor)) {
            user.getColors().remove(playerColor);
        } else {
            user.getColors().add(playerColor);
        }
        sender.sendMessage(ChatUtils.message("&aAdded the color: " + playerColor.toString().toUpperCase()));
        if (user.isOnline()) {
            user.msg(ChatUtils.message("&eYou have been granted the " + PlayerColor.valueOf(playerColor.toString().toUpperCase()).getColor() + playerColor.toString().toUpperCase() + " &ecolor!"));
        }

    }
}
