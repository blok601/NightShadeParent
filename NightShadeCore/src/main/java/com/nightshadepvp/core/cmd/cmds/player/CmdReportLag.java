package com.nightshadepvp.core.cmd.cmds.player;

import co.aikar.timings.Timings;
import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.UUID;

public class CmdReportLag extends NightShadeCoreCommand {

    private static CmdReportLag i = new CmdReportLag();
    public static CmdReportLag get() {return i;}

    boolean coolDown = false;

    public CmdReportLag(){
        this.addAliases("reportlag");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        if(coolDown){
            nsPlayer.msg(ChatUtils.message("&cA lag report has already recently been generated! Please wait a little before doing this again!"));
            return;
        }

        nsPlayer.msg(ChatUtils.message("&ePlease send the following message to Blok!"));
        Timings.generateReport(sender);
        this.coolDown = true;
        new BukkitRunnable(){
            @Override
            public void run() {
                coolDown = false;
            }
        }.runTaskLater(Core.get(), 20*60*2); //2 mins

    }
}
