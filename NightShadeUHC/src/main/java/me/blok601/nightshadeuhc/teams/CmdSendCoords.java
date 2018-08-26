package me.blok601.nightshadeuhc.teams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSendCoords implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String arg2, String[] args) {
		if(s instanceof Player){
			Player p = (Player) s;
			if(TeamManager.getInstance().getTeam(p) == null){
				p.sendMessage(ChatColor.DARK_PURPLE + "[UHC] " + ChatColor.RED + "You are not on a team!");
				return false;
			}
			
			Location l = p.getLocation();
			Double  x = Math.ceil(l.getX());
			Double y = Math.ceil(l.getY());
			Double z =  Math.ceil(l.getZ());
			for (String str : TeamManager.getInstance().getTeam(p).getMembers()){
				if(Bukkit.getPlayer(str) == null) continue;
				Bukkit.getPlayer(str).sendMessage(ChatColor.DARK_AQUA + "[Team] " + ChatColor.GOLD + p.getName() + ChatColor.AQUA + " " + x + "X " + y + "Y " + z + "Z");
			}
		}else{
			return false;
		}
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
