package me.blok601.nightshadeuhc.commands.extras;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class eWhitelistCommand implements UHCCommand{
    @Override
    public String[] getNames() {
        return new String[]{
                "ewl"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        if(s instanceof Player){
            return;
        }

        if(args.length != 2){
            return;
        }

        if(args[0].equalsIgnoreCase("add")){
            String target = args[1];
            if(GameManager.get().getWhitelist().contains(target.toLowerCase())){
                return;
            }

            GameManager.get().getWhitelist().add(target.toLowerCase());
            Bukkit.getConsoleSender().sendMessage(ChatUtils.format("&a" + target + " has been added to the whitelist!"));
        }else if(args[0].equalsIgnoreCase("remove")){
            String target = args[1];
            if(!GameManager.get().getWhitelist().contains(target.toLowerCase())){
                return;
            }

            GameManager.get().getWhitelist().remove(target.toLowerCase());
            Bukkit.getConsoleSender().sendMessage(ChatUtils.format("&a" + target + " has been removed from the whitelist!"));
        }else{
            return;
        }
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public Rank getRequiredRank() {
        return null;
    }

    @Override
    public boolean hasRequiredRank() {
        return false;
    }
}
