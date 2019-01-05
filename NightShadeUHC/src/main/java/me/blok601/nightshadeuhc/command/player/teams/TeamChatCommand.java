package me.blok601.nightshadeuhc.command.player.teams;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TeamChatCommand implements UHCCommand {
	
	public static ArrayList<Player> tchat = new ArrayList<Player>();


	@Override
	public String[] getNames() {
		return new String[]{
				"pm"
		};
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		Player p = (Player) sender;
			if(TeamManager.getInstance().getTeam(p) == null){
				p.sendMessage(ChatUtils.message("&cYou are not on a team!"));
				return;
			}
			
			if(args.length > 0){
				StringBuilder message = new StringBuilder();
				
				for(int i = 0; i < args.length; i++){
					message.append(args[i]).append(" ");
				}
				
				String r = message.toString().trim();
				TeamManager.getInstance().getTeam(p).message("&6" + p.getName() + ": &b" + r);
				
				
			}else{
				p.sendMessage(ChatUtils.message("&cUse the command like this! /pm <message>"));
				return;
			}
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
