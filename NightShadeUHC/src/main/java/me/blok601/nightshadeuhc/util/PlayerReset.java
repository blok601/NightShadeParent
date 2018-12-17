package me.blok601.nightshadeuhc.util;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class PlayerReset implements UHCCommand{


	@Override
	public String[] getNames() {
		return new String[]{
				"resetall"
		};
	}

	@Override
	public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
		for (Player pls : Bukkit.getOnlinePlayers()){
			pls.getInventory().setHelmet(null);
			pls.getInventory().setChestplate(null);
			pls.getInventory().setLeggings(null);
			pls.getInventory().setBoots(null);
			pls.getInventory().clear();
			for (PotionEffect eff : pls.getActivePotionEffects()){
				pls.removePotionEffect(eff.getType());
			}

			pls.setExp(0.0F);
			pls.setLevel(0);
			pls.setFoodLevel(20);
			pls.setHealth(pls.getMaxHealth());
		}
	}

	@Override
	public boolean playerOnly() {
		return true;
	}

	@Override
	public Rank getRequiredRank() {
		return Rank.MOD;
	}

	@Override
	public boolean hasRequiredRank() {
		return true;
	}
}
