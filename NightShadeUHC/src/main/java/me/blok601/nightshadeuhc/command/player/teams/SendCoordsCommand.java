package me.blok601.nightshadeuhc.command.player.teams;

import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendCoordsCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String arg2, String[] args) {
		if(s instanceof Player){
			Player p = (Player) s;
			if(TeamManager.getInstance().getTeam(p) == null){
				p.sendMessage(ChatUtils.message("&cYou are not on a team!"));
				return false;
			}
			
			Location l = p.getLocation();
			Double  x = Math.ceil(l.getX());
			Double y = Math.ceil(l.getY());
			Double z =  Math.ceil(l.getZ());
			TeamManager.getInstance().getTeam(p).message("&6" + p.getName() + " &b" + x + "X " + y + "Y " + z + "Z");
		}else{
			return false;
		}
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
