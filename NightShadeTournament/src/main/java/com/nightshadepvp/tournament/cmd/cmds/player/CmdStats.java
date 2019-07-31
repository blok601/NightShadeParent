package com.nightshadepvp.tournament.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.gui.StatsGUI;
import com.nightshadepvp.tournament.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 8/8/2018.
 */
public class CmdStats extends NightShadeTournamentCommand {

    private static CmdStats i = new CmdStats();
    public static CmdStats get() {return i;}

    public CmdStats() {
        this.addAliases("stats");
        this.addParameter(TypeString.get(), "player");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        String targetString = this.readArg();
        TPlayer tPlayer = TPlayer.get(sender);

        Player target = Bukkit.getPlayer(targetString);
        if(target == null){
            //Offline
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(targetString);
            if(offlinePlayer == null){
                tPlayer.msg(ChatUtils.message("&cThat player couldn't be found!"));
                return;
            }

            TPlayer targetTplayer = TPlayer.get(offlinePlayer.getUniqueId());
            if(targetTplayer == null || !targetTplayer.hasPlayedBefore()){
                tPlayer.msg(ChatUtils.message("&cThat player has never joined the server!"));
                return;
            }

            new StatsGUI(targetTplayer, tPlayer.getPlayer());
            return;
        }

        TPlayer targetTplayer = TPlayer.get(target);
        new StatsGUI(targetTplayer, tPlayer.getPlayer());

    }
}
