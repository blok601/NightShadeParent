package com.nightshadepvp.tournament.cmd.cmds.admin.kits;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanTrue;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.handler.KitHandler;
import com.nightshadepvp.tournament.entity.objects.data.Kit;
import com.nightshadepvp.tournament.utils.ChatUtils;

/**
 * Created by Blok on 8/8/2018.
 */
public class CmdKitSetRegen extends NightShadeTournamentCommand {

    public CmdKitSetRegen() {
        this.addAliases("setregen");
        this.addParameter(TypeString.get(), "kit");
        this.addParameter(TypeBooleanTrue.get(), "true/false");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.ADMIN));
        this.setVisibility(Visibility.SECRET);
    }

    @Override
    public void perform() throws MassiveException {
        String kitName = this.readArg();
        boolean toggle = this.readArg();
        TPlayer tPlayer = TPlayer.get(sender);
        if(!KitHandler.getInstance().isKit(kitName)){
            tPlayer.msg(ChatUtils.message("&cThat is not a kit!"));
            return;
        }
        Kit kit = KitHandler.getInstance().getKit(kitName);
        kit.setAllowRegen(toggle);
        tPlayer.msg(ChatUtils.message("&bRegen is now&8: " + (kit.isAllowRegen() ? "&atrue" : "&cfalse") + "&b for kit&8: &f" + kit.getName() + "&b."));
    }
}
