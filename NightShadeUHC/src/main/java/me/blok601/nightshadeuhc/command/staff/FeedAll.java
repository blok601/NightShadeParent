package me.blok601.nightshadeuhc.command.staff;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.command.UHCCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class FeedAll implements UHCCommand{

	@Override
	public String[] getNames() {
		return new String[]{
			"feedall"
		};
	}

	@Override
	public void onCommand(CommandSender s, Command cmd, String arg2,
			String[] args) {
//		for (Player pl : Bukkit.getOnlinePlayers()){
//			pl.setFoodLevel(20);
//		}

		Bukkit.getOnlinePlayers().forEach(o -> o.setFoodLevel(20));
		ChatUtils.sendAll("Everyone has been fed!");
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
	public boolean hasRequiredRank(){
		return true;
	}


}
