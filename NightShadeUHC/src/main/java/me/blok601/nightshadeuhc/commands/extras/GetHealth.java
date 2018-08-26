package me.blok601.nightshadeuhc.commands.extras;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class GetHealth implements CmdInterface {

	@Override
	public String[] getNames() {
		return new String[]{
				"h", "health", "hp"
		};
	}

	@Override
	public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
		Player p = (Player) s;

		if (args.length == 0) {
			double pct = ((p.getHealth()) / 2) * 10;

			String h = null;
			DecimalFormat df = new DecimalFormat("0.0");
			h = df.format(pct);

			p.sendMessage(ChatUtils.message("&b" + p.getName() + "'s Health: "
					+ h));
			return;
		} else if (args.length == 1) {
			Player target = Bukkit.getPlayerExact(args[0]);
			if (target == null) {
				p.sendMessage(ChatUtils.message("&cCouldn't find that player!"));
				return;
			}
			double pct = ((target.getHealth()) / 2);

			String h = null;
			DecimalFormat df = new DecimalFormat("0.0");
			h = df.format(pct);

			p.sendMessage(ChatUtils.message("&c" + target.getName()
					+ "'s Health: " + h));

		} else {
			p.sendMessage(ChatUtils.message("&cUsage: /h [player]"));
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
