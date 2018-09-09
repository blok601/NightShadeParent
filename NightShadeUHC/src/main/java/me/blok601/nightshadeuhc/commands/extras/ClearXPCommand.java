package me.blok601.nightshadeuhc.commands.extras;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 9/4/2018.
 */
public class ClearXPCommand implements CmdInterface {
    @Override
    public String[] getNames() {
        return new String[]{
                "clearxp"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        UHCPlayer uhcPlayer = UHCPlayer.get(s);


        if(args.length == 0){
            uhcPlayer.getPlayer().setExp(0);
            uhcPlayer.getPlayer().setLevel(0);
            uhcPlayer.msg(ChatUtils.message("&eYou have cleared your XP!"));
            return;
        }

        if(args.length == 1){
            if(args[0].equalsIgnoreCase("*") || args[0].equalsIgnoreCase("all")){
                for (UHCPlayer uhcPlayer1 : UHCPlayerColl.get().getAllOnline()){
                    uhcPlayer1.getPlayer().setExp(0F);
                    uhcPlayer1.getPlayer().setLevel(0);
                }
                uhcPlayer.msg(ChatUtils.message("&eXP has been &acleared!"));
                return;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if(target == null){
                uhcPlayer.msg(ChatUtils.message("&cThat player couldn't be found!"));
                return;
            }

            target.setExp(0);
            target.setLevel(0);
        }else{
            uhcPlayer.msg(ChatUtils.message("&cUsage: /clearxp <*/player> (No argument clears your own XP)"));
        }

        uhcPlayer.msg(ChatUtils.message("&eXP has been &acleared!"));
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.TRIALHOST;
    }

    @Override
    public boolean hasRequiredRank() {
        return true;
    }
}
