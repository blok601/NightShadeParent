package com.nightshadepvp.tournament.cmd.cmds.admin;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.TPlayerColl;
import com.nightshadepvp.tournament.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class CmdWipeStats extends NightShadeTournamentCommand {

    private static CmdWipeStats i = new CmdWipeStats();
    public static CmdWipeStats get() {return i;}

    public CmdWipeStats() {
        this.addAliases("wipestats");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.ADMIN));
        this.setVisibility(Visibility.SECRET);
        this.addParameter(TypeString.get(), "player/*");
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        String arg = this.readArg();

        if(arg.equalsIgnoreCase("*") || arg.equalsIgnoreCase("all")){
            if(!nsPlayer.hasRank(Rank.OWNER)){
                nsPlayer.msg(ChatUtils.message("&cThis is an owner only command!"));
                return;
            }

            nsPlayer.msg(ChatUtils.message("&bClearing all player stats on &fTournament..."));
            nsPlayer.msg(ChatUtils.message("&bThis is a heavy process and may take some time"));
            int count = 0;
            long start = System.currentTimeMillis();
            for (TPlayer tPlayer : TPlayerColl.get().getAll()){
                tPlayer.setFightsWon(0);
                tPlayer.setFightsLost(0);
                tPlayer.setTournamentsWon(0);
                tPlayer.setTournamentsPlayed(0);
                count++;
            }
            long totalTimeInMillis = start - System.currentTimeMillis();
            long seconds = totalTimeInMillis / 1000;
            long millis = totalTimeInMillis % 1000;
            ChatUtils.broadcast("&fCleared the stats of &f" + count + " &bplayers in &f" + seconds + "." + millis + "s");
        }else{
            if(Bukkit.getPlayer(arg) == null){
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(arg);
                if(offlinePlayer == null){
                    nsPlayer.msg(ChatUtils.message("&cThat player doesn't exist!"));
                    return;
                }

                if(!offlinePlayer.hasPlayedBefore()){
                    nsPlayer.msg(ChatUtils.message("&cThat player has never joined!"));
                    return;
                }

                TPlayer tPlayer = TPlayer.get(offlinePlayer.getUniqueId());
                tPlayer.setFightsWon(0);
                tPlayer.setFightsLost(0);
                tPlayer.setTournamentsWon(0);
                tPlayer.setTournamentsPlayed(0);
                nsPlayer.msg(ChatUtils.message("&bSuccessfully cleared the stats of &f" + tPlayer.getName()));
            }else{
                Player target = Bukkit.getPlayer(arg);
                TPlayer tPlayer = TPlayer.get(target);
                tPlayer.setFightsWon(0);
                tPlayer.setFightsLost(0);
                tPlayer.setTournamentsWon(0);
                tPlayer.setTournamentsPlayed(0);
                nsPlayer.msg(ChatUtils.message("&bSuccessfully cleared the stats of &f" + tPlayer.getName()));
            }
        }
    }
}
