package me.blok601.nightshadeuhc.command.player.teams;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendCoordsCommand implements UHCCommand {

	@Override
	public String[] getNames() {
		return new String[]{
				"pmcoords"
		};
	}

	@Override
	public void onCommand(CommandSender s, Command cmd, String arg2, String[] args) {
			Player p = (Player) s;
			if(TeamManager.getInstance().getTeam(p) == null){
				p.sendMessage(ChatUtils.message("&cYou are not on a team!"));
				return;
			}
			
			Location l = p.getLocation();
			Double  x = Math.ceil(l.getX());
			Double y = Math.ceil(l.getY());
			Double z =  Math.ceil(l.getZ());
			TeamManager.getInstance().getTeam(p).message("&6" + p.getName() + " &b" + x + "X " + y + "Y " + z + "Z");
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
