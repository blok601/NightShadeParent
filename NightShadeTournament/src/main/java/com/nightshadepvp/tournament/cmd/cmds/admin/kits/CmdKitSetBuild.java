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
public class CmdKitSetBuild extends NightShadeTournamentCommand {

    public CmdKitSetBuild() {
        this.addAliases("setbuild");
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
        kit.setBuild(toggle);
        tPlayer.msg(ChatUtils.message("&eBuilding is now&8: " + (kit.isBuild() ? "&atrue" : "&cfalse") + "&e for kit&8: &3" + kit.getName() + "&e."));
    }
}
