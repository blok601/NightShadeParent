package me.blok601.nightshadeuhc.commands.extras;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.UUID;

public class Helpop implements CmdInterface{

	private HashSet<UUID> helpopCooldown = new HashSet<>();

	@Override
	public String[] getNames() {
		return new String[]{
				"helpop", "request"
		};
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String l, String[] args) {
		Player p = (Player) sender;
		NSPlayer user = NSPlayer.get(p.getUniqueId());
		if(user.getRank().getValue() >= Rank.TRIAL.getValue()){
			p.sendMessage(ChatColor.DARK_AQUA + "[UHC] " + ChatColor.RED + "Staff members can't speak in helpop! Use /sc!");
			return;
		}else{

		    if(helpopCooldown.contains(p.getUniqueId())){
		        p.sendMessage(ChatUtils.message("&cYou can only use helpop every 3 seconds!"));
		        return;
            }



			if(args.length == 0){
				p.sendMessage(ChatColor.DARK_AQUA + "[UHC] " + ChatColor.RED + "Usage: /helpop <message>");
				return;
			}else{

				if(GameManager.getHelpOpMutes().containsKey(p.getUniqueId())){
					if(!GameManager.getHelpopMuteReasons().containsKey(p.getUniqueId())){
						//Check if they both have it
						GameManager.getHelpopMuteReasons().remove(p.getUniqueId());
						GameManager.getHelpOpMutes().remove(p.getUniqueId());
						return;
					}

					//They both have
					p.sendMessage(ChatUtils.message("&cYou are currently helpop muted for&8: &3" + TimeUtils.formatSeconds(GameManager.getHelpOpMutes().get(p.getUniqueId())) + " &efor&8: &3" + GameManager.getHelpopMuteReasons().get(p.getUniqueId())));
					return;
				}

				StringBuilder message = new StringBuilder();

				for(int i = 0; i < args.length; i++){
					message.append(args[i]).append(" ");
				}

				String reason = message.toString().trim();


				Bukkit.getOnlinePlayers().stream().filter(o -> NSPlayer.get(o.getUniqueId()).hasRank(Rank.TRIAL)).filter(o -> UHCPlayer.get(o.getUniqueId()).isReceiveHelpop()).forEach(o -> o.sendMessage(ChatColor.DARK_AQUA + "[Helpop] "+  p.getName() + ": "+ ChatColor.YELLOW + reason));
                this.helpopCooldown.add(p.getUniqueId());
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        helpopCooldown.remove(p.getUniqueId());
                    }
                }.runTaskLater(UHC.get(), 20*5);
				p.sendMessage(ChatColor.DARK_AQUA + "[UHC] " + ChatColor.YELLOW + "Your message was sent to the online moderators");
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
		return null;
	}

	@Override
	public boolean hasRequiredRank() {
		return false;
	}
}
