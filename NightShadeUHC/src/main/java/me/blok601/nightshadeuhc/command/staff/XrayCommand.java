package me.blok601.nightshadeuhc.command.staff;

import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.lunar.api.type.StaffModule;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class XrayCommand implements UHCCommand {

    @Override
    public String[] getNames() {
        return new String[] {
                "xray"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player player = (Player) s;
        UHCPlayer uhcPlayer = UHCPlayer.get(s);
        if(!uhcPlayer.isSpectator()){
            uhcPlayer.msg(ChatUtils.message("&cXray can only be enabled while spectating!"));
            return;
        }

        if(args.length != 1){
            uhcPlayer.msg(ChatUtils.message("&cUsage: /xray <on/off>"));
            return;
        }

        if(args[0].equalsIgnoreCase("on")){
            try {
                Core.get().getApi().toggleStaffModule(player, StaffModule.XRAY, true);
                uhcPlayer.msg(ChatUtils.message("&aEnabled xray!"));
                Core.get().getApi().toggleStaffModule(player, StaffModule.BUNNY_HOP, true);
                Core.get().getApi().toggleStaffModule(player, StaffModule.NAME_TAGS, true);
            } catch (IOException e) {
                e.printStackTrace();
                uhcPlayer.msg(ChatUtils.message("&cThere was an error trying to enable xray!"));
                return;
            }
        }else if(args[0].equalsIgnoreCase("off")){
            try {
                Core.get().getApi().toggleStaffModule(player, StaffModule.XRAY, false);
            } catch (IOException e) {
                e.printStackTrace();
                uhcPlayer.msg(ChatUtils.message("&cThere was an error trying to disable xray!"));
                return;
            }
        }else{
            uhcPlayer.msg(ChatUtils.message("&cUsage: /xray <on/off>"));
            return;
        }

    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.TRIAL;
    }

    @Override
    public boolean hasRequiredRank() {
        return true;
    }
}
