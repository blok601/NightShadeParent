package me.blok601.nightshadeuhc.teams;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 9/24/2017.
 */
public class TeamsCommand implements UHCCommand{
    @Override
    public String[] getNames() {
        return new String[]{
                "teams"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;

        if(!GameManager.get().isIsTeam()){
            p.sendMessage(ChatUtils.message("&cIt is not a teams game!"));
            return;
        }

        p.sendMessage(ChatUtils.format("&5&m-----------------------------------"));
        StringBuilder stringBuilder;
        for (Team team : TeamManager.getInstance().getTeams()){
            stringBuilder = new StringBuilder();
            for (String string : team.getMembers()){
                Player member = Bukkit.getPlayer(string);
                if(member == null){
                    stringBuilder.append("&7" + string);
                }else{
                    stringBuilder.append("&a" + string);
                }

                stringBuilder.append(", ");
            }

            String f = stringBuilder.toString().trim();
            p.sendMessage(ChatUtils.format("&e" + team.getName() + "&8: &5" + f.substring(0, stringBuilder.toString().length()-1)));
        }
        p.sendMessage(ChatUtils.format("&5&m-----------------------------------"));

    }

    @Override
    public boolean playerOnly() {
        return true;
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
