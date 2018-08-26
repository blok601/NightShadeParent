package com.nightshadepvp.core.cmd.cmds.admin;

import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.cmd.type.TypePlayerEffect;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.objects.PlayerEffect;
import com.nightshadepvp.core.utils.ChatUtils;

/**
 * Created by Blok on 7/6/2018.
 */
public class CmdAddEffect extends NightShadeCoreCommand{

    private static CmdAddEffect i = new CmdAddEffect();
    public static CmdAddEffect get() {return  i;}

    public CmdAddEffect() {

        this.addAliases("addeffect");
        this.addParameter(TypeNSPlayer.get(), "player");
        this.addParameter(TypePlayerEffect.get(), "effect");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.ADMIN));
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer targetUser = this.readArg();
        PlayerEffect targetEffect = this.readArg();


        if(targetUser.getEffects().contains(targetEffect)){
            sender.sendMessage(ChatUtils.message("&cThat player already has that effect!"));
            return;
        }

        targetUser.getEffects().add(targetEffect);
        if(targetUser.isOnline()){
            targetUser.msg(ChatUtils.message("&eYou have been granted the &a" + targetEffect.getName() + " &eeffect!"));
        }
        sender.sendMessage(ChatUtils.message("&eYou have granted &a" + targetUser.getName() + " &ethe &a" + targetEffect.getName() + " &effect!"));
    }
}
