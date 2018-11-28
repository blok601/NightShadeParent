package me.blok601.nightshadeuhc.commands.extras;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeAll implements UHCCommand{

	boolean freezeAll = false;

	@Override
	public String[] getNames() {
		return new String[]{
				"freezeall"
		};
	}

	@Override
	public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
		Player p = (Player) s;
		if(freezeAll){
			Freeze.stop();
			freezeAll = false;
			p.sendMessage(ChatUtils.message("&aEveryone has been unfrozen!"));
			return;
		}else{
			Freeze.start();
			freezeAll = true;
			p.sendMessage(ChatUtils.message("&cEveryone has been frozen!"));
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
