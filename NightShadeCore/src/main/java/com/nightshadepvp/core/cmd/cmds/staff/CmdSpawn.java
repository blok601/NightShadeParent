package com.nightshadepvp.core.cmd.cmds.staff;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.MConf;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.entity.Player;

public class CmdSpawn extends NightShadeCoreCommand {

    private static CmdSpawn i = new CmdSpawn();
    public static CmdSpawn get() {return i;}

    public CmdSpawn() {
        this.addAliases("spawn");
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

            target.teleport(MConf.get().getSpawnLocation());
            nsPlayer.msg(ChatUtils.message("&bSent &f" + target.getName() + " &bto spawn!"));
            return;
        }

        if(!this.argIsSet()){
            nsPlayer.msg(ChatUtils.message("&cConsole Usage: /spawn <player>"));
            return;
        }
        Player target = this.readArg();
        if(target == null){
            nsPlayer.msg(ChatUtils.message("&cThat player couldn't be found!"));
            return;
        }

        target.teleport(MConf.get().getSpawnLocation());
        nsPlayer.msg(ChatUtils.message("&bSent &f" + target.getName() + " &bto spawn!"));
        return;

    }
}
