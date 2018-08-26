package com.nightshadepvp.core.cmd.cmds.staff;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.punishment.PunishmentHandler;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 7/6/2018.
 */
public class CmdPunish extends NightShadeCoreCommand{

    private static CmdPunish i = new CmdPunish();
    public static CmdPunish get() {return i;}


    public CmdPunish() {
        this.addAliases("punish") ;
        this.addParameter(TypeString.get(), "player");
        this.addRequirements(RequirementIsPlayer.get());
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));
    }

    @Override
    public void perform() throws MassiveException {
        Player p = (Player) sender;
        String target = this.readArg();
        PunishmentHandler.getInstance().getPunishing().put(p, target);
        PunishmentHandler.getInstance().createGUI(p);
    }
}
