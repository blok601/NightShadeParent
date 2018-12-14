package me.blok601.nightshadeuhc.commands.extras;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PvPCommand implements UHCCommand{

	@Override
	public String[] getNames() {
		return new String[]{
				"pvp"
		};
	}

	@Override
	public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
		Player p = (Player) s;
			if(args.length == 0){
				p.sendMessage(ChatColor.DARK_AQUA + "[UHC] " + ChatColor.RED + "Use the command like this! /pvp <on/off> [world]. If no world is specified then pvp will be toggled globally!");
				return;
			}else if(args.length == 1){
				if(args[0].equalsIgnoreCase("on")){
					if(pvp){
						p.sendMessage(ChatColor.DARK_AQUA + "[UHC] " + ChatColor.RED + "PvP is already on!");
						return;
					}
					enablePvP();
					p.sendMessage(ChatColor.DARK_AQUA + "[UHC] " + ChatColor.GREEN + "Globally enabled PvP!");
					return;

				}else if(args[0].equalsIgnoreCase("off")){
					if(!pvp){
						p.sendMessage(ChatColor.DARK_AQUA + "[UHC] " + ChatColor.RED + "PvP is already off!");
						return;
					}

					disablePvP();
					p.sendMessage(ChatColor.DARK_AQUA + "[UHC] " + ChatColor.GREEN + "Globally disabled PvP!");
					return;
				}else{
					p.sendMessage(ChatColor.DARK_AQUA + "[UHC] " + ChatColor.RED + "Use the command like this! /pvp <on/off> [world]. If no world is specified then pvp will be toggled globally!");
					return;
				}
			}else if(args.length == 2){
				World wr = Bukkit.getWorld(args[1]);
				if(wr == null){
					p.sendMessage(ChatColor.DARK_AQUA + "[UHC] " + ChatColor.RED + "Couldn't find that world!");
					return;
				}

				if(args[0].equalsIgnoreCase("on")){
					if(wr.getPVP()){
						p.sendMessage(ChatColor.DARK_AQUA + "[UHC] " + ChatColor.RED + "PvP already on in that world!");
						return;
					}

					enablePvP(wr);
					p.sendMessage(ChatColor.DARK_AQUA + "[UHC] " + ChatColor.GREEN + "Enabled PvP in world " + wr.getName());
					return;
				}else if(args[0].equalsIgnoreCase("off")){
					if(!wr.getPVP()){
						p.sendMessage(ChatColor.DARK_AQUA + "[UHC] " + ChatColor.RED + "PvP already off in that world!");
						return;
					}

					disablePvP(wr);
					p.sendMessage(ChatColor.DARK_AQUA + "[UHC] " + ChatColor.GREEN + "Disabled PvP in world " + wr.getName());
					return;
				}
			}
		}

	@Override
	public boolean playerOnly() {
		return false;
	}

	@Override
	public Rank getRequiredRank() {
		return Rank.TRIAL;
	}

	@Override
	public boolean hasRequiredRank() {
		return true;
	}

	public static void enablePvP(){
		for (World wo : Bukkit.getWorlds()){
			wo.setPVP(true);
		}
		pvp = true;
	}
	
	public static void disablePvP(){
		for (World wo : Bukkit.getWorlds()){
			wo.setPVP(false);
		}
		
		pvp = false;
	}
	
	public static void enablePvP(World w){
		w.setPVP(true);
		pvp = true;
	}
	
	public static void disablePvP(World w){
		w.setPVP(false);
	}
	
	public static boolean isEnabled(){
		return pvp;
	}

	public static boolean pvp = false;


}
