package com.nightshadepvp.core.cmd.cmds.staff;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.entity.Player;

public class CmdClearArmor extends NightShadeCoreCommand {

    private static CmdClearArmor i = new CmdClearArmor();
    public static CmdClearArmor get () {return i;}

    public CmdClearArmor() {
        this.addAliases("cleararmor", "ca");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));
        this.addParameter(TypePlayer.get(), "target");
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        if(nsPlayer.isPlayer()){
            Player target = this.readArg(nsPlayer.getPlayer());
            if(target == null){
                nsPlayer.msg(ChatUtils.message("&cThat player couldn't be found!"));
                return;
            }
            target.getInventory().setArmorContents(null);
            nsPlayer.msg(ChatUtils.message("&bCleared the armor of &f" + target.getName()));
            return;
        }

        if(!this.argIsSet()){
            nsPlayer.msg(ChatUtils.message("&cConsole Usage: /cleararmor <player>"));
            return;
        }
    }
}
