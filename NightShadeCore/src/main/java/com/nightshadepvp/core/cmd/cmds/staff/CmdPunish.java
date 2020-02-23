package com.nightshadepvp.core.cmd.cmds.staff;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.punishment.PunishmentHandler;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 7/6/2018.
 */
public class CmdPunish extends NightShadeCoreCommand{

    private static CmdPunish i = new CmdPunish();
    public static CmdPunish get() {return i;}


    public CmdPunish() {
        this.addAliases("punish") ;
        this.addParameter(TypeNSPlayer.get(), "player");
        this.addRequirements(RequirementIsPlayer.get());
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));

    }

    @Override
    public void perform() throws MassiveException {
        Player p = (Player) sender;
        NSPlayer target = this.readArg();

        if(target == null){
            p.sendMessage(ChatUtils.message("&cThat player could not be found!"));
            return;
        }

        if(!target.hasPlayedBefore()){
            p.sendMessage(ChatUtils.message("&cThat player has never logged in before! Please contact an admin if this player needs to be punished!"));
            return;
        }

        if(target.isConsole()){
            p.sendMessage(ChatUtils.message("&cYou can't punish the console!"));
            return;
        }

        if(target.hasRank(NSPlayer.get(p).getRank())){
            p.sendMessage(ChatUtils.message("&cYou can't punish those with the same rank or higher as you."));
            return;
        }

        PunishmentHandler.getInstance().getPunishing().put(p, target.getName());
        PunishmentHandler.getInstance().createGUI(p);
    }
}
