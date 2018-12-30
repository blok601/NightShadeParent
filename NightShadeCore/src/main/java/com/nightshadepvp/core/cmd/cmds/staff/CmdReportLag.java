package com.nightshadepvp.core.cmd.cmds.staff;

import co.aikar.timings.Timings;
import co.aikar.timings.TimingsCommand;
import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

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

        nsPlayer.msg(ChatUtils.message("&eA lag report will be generated over the next few minutes. Please be patient."));
        Timings.setTimingsEnabled(false);
        Timings.reset();
        Timings.setTimingsEnabled(true);
        this.coolDown = true;
        new BukkitRunnable(){
            @Override
            public void run() {
                nsPlayer.msg(ChatUtils.format("&4&m-----------&r&4Lag Report Start&4&m-----------"));
                nsPlayer.msg(ChatUtils.message("&cSend the exact link to Blok so he can fix the problems!"));
                Timings.generateReport(nsPlayer.getPlayer());
                nsPlayer.msg(ChatUtils.format("&4&m-----------&r&4Lag Report Finish&4&m-----------"));
                coolDown = false;
            }
        }.runTaskLater(Core.get(), 20*60*2);

    }
}
