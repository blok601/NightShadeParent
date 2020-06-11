package com.nightshadepvp.core.cmd.cmds.admin;

import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Blok on 7/6/2018.
 */
public class CmdRestart extends NightShadeCoreCommand{

    private static CmdRestart i = new CmdRestart();
    public static CmdRestart get() {return i;}

    public CmdRestart() {
        this.addAliases("restart");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.STAFF));
    }

    @Override
    public void perform() throws MassiveException {
        Bukkit.broadcastMessage(ChatUtils.message("&6The Server Will Restart in 5 seconds!"));
        Core.get().getLogManager().log(Logger.LogType.SERVER, "The server is restarting in ~5 seconds!");
        new BukkitRunnable(){
            int counter = 7;
            @Override
            public void run() {
                if(counter == 5){
                    Bukkit.getOnlinePlayers().forEach(o -> o.kickPlayer("Thanks for playing on NightShadePvP\n Join our Discord @ discord.nightshadepvp.com for updates   \n" +
                            "Follow us on Twitter @NightShadePvPMC for news and information!"));
                }

                if(counter == 0){
                    this.cancel();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spigot:restart");
                    return;
                }

                counter--;
            }
        }.runTaskTimer(Core.get(), 0, 20);
    }
}
