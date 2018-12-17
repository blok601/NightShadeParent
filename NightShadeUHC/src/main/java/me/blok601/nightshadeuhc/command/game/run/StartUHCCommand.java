package me.blok601.nightshadeuhc.command.game.run;


import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.util.Freeze;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.task.ScatterTask;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class StartUHCCommand implements UHCCommand {

	public static ArrayList<String> arrayList = new ArrayList<>();


	@Override
	public String[] getNames() {
		return new String[]{
				"suhc"
		};
	}

	@Override
	public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
		final Player p = (Player) s;
			if(args.length != 1){
				p.sendMessage(ChatColor.RED + "Usage: /startuhc <radius>");
				return;
			}else{

				if(GameManager.get().getWorld() == null){
					p.sendMessage(ChatUtils.message("&cThe world wasn't set!"));
					return;
				}

				if(GameManager.get().getHost() == null){
					p.sendMessage(ChatUtils.message("&cThe host has not been set!"));
					return;
				}

				if(!Util.isInt(args[0])){
					p.sendMessage(ChatUtils.message("&cThat is not a number!"));
					return;
				}

				GameManager.get().setRadius(Integer.parseInt(args[0]));

				ArrayList<Player> valid = new ArrayList<>();
				UHCPlayer gp;
				for (Player ps : Bukkit.getOnlinePlayers()){
					gp = UHCPlayer.get(ps.getUniqueId());
					if(!gp.isSpectator()){
						valid.add(ps);
						if(UHC.players.contains(ps.getUniqueId())) continue;
						UHC.players.add(ps.getUniqueId());
					}
				}

				arrayList.add(p.getName());
				p.sendMessage(ChatUtils.message("&6Your game will start in 10 seconds with options: &7Final Heal: &6" +
                        GameManager.get().getFinalHealTime()/60 + " &7PvP Time: &6" +
                        GameManager.get().getPvpTime()/60 + " &7Meetup Time: &6" +
                        GameManager.get().getBorderTime()/60 + " &7World: &6" +
                        GameManager.get().getWorld().getName() + " &7Teams: &6" +
                        Boolean.toString(GameManager.get().isIsTeam()) + " &7First Shrink: &6" +
                        GameManager.get().getFirstShrink()));
				p.sendMessage(ChatUtils.message("&eDo /cancelgame to stop the game before 10 seconds has elapsed!"));
				new BukkitRunnable(){
					@Override
					public void run(){
						if(arrayList.contains(p.getName())){
							GameManager.get().getWorld().setTime(20);
							ChatUtils.setChatFrozen(true);
							Bukkit.broadcastMessage(ChatUtils.message("&9Use /helpop or message any online staff members if you need help!"));
							Freeze.start();
							UHCPlayerColl.get().getAllOnline().stream().filter(UHCPlayer::isInArena).forEach(UHCPlayer::leaveArena);
							GameState.setState(GameState.STARTING);
							new ScatterTask(valid, GameManager.get().getWorld(), GameManager.get().getRadius(), GameManager.get().getHost(), GameManager.get().getFinalHealTime(), GameManager.get().getPvpTime(), GameManager.get().getBorderTime(), GameManager.get().isIsTeam(), GameManager.get().getFirstShrink(), GameManager.get().getMeetupTime()).runTaskTimer(UHC.get(), 0, 4);
						}else{
							arrayList.remove(p.getName());
						}
					}
				}.runTaskLater(UHC.get(), 10*20);
			}
	}

	@Override
	public boolean playerOnly() {
		return true;
	}

	@Override
	public Rank getRequiredRank() {
		return Rank.HOST;
	}

	@Override
	public boolean hasRequiredRank() {
		return true;
	}
}
