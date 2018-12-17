package me.blok601.nightshadeuhc.util;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class Freeze {

	private static HashSet<UUID> toFreeze = new HashSet<>();

	public static void start(){
		NSPlayer user;
		UHCPlayer gamePlayer;
		for (Player p : Bukkit.getOnlinePlayers()){
			user = NSPlayer.get(p.getUniqueId());
			gamePlayer = UHCPlayer.get(p.getUniqueId());

			if(gamePlayer == null || user == null) continue;

			if(user.hasRank(Rank.TRIAL)){
				p.sendMessage(ChatUtils.message("&eYou were not frozen since you are a staff member!"));
				continue;
			}

			if(gamePlayer.isSpectator()){
				continue;
			}

			gamePlayer.setFrozen(true);
			p.sendMessage(ChatUtils.message("&eYou have been frozen!"));

		}
	}

	public static void stop(){
		UHCPlayer gamePlayer;
		for (Player p : Bukkit.getOnlinePlayers()){
			gamePlayer = UHCPlayer.get(p.getUniqueId());
			if(gamePlayer == null) continue;
			if(gamePlayer.isFrozen()){
				gamePlayer.setFrozen(false);
				p.sendMessage(ChatUtils.message("&eYou were unfrozen!"));
			}
		}
	}

	public static HashSet<UUID> getToFreeze() {
		return toFreeze;
	}
}
