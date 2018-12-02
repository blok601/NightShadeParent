package me.blok601.nightshadeuhc.teams;

import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CmdTeamChat implements CommandExecutor{
	
	public static ArrayList<Player> tchat = new ArrayList<Player>();


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player){
			Player p = (Player) sender;	
			if(TeamManager.getInstance().getTeam(p) == null){
				p.sendMessage(ChatUtils.message("&cYou are not on a team!"));
				return false;
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
				return false;
			}
		}else{
			return false;
		}
		return false;
	}

}
