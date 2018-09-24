package me.blok601.nightshadeuhc.staff.spec;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SpecEvents implements Listener {

	@EventHandler
	public void pickup(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		UHCPlayer gamePlayer = UHCPlayer.get(p.getUniqueId());
		if (gamePlayer.isSpectator()) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void hit(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player p = (Player) e.getEntity();

			UHCPlayer gamePlayer = UHCPlayer.get(p.getUniqueId());
			if(gamePlayer.isInArena()){
				return;
			}
			if (gamePlayer.isSpectator()) {
				e.setCancelled(true);
			}

		}
	}

	@EventHandler
	public void damage(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			UHCPlayer gamePlayer = UHCPlayer.get(e.getEntity().getUniqueId());
			if(gamePlayer.isInArena()){
				return;
			}
			if (gamePlayer.isSpectator()) {
				e.setCancelled(true);
			}
		}
		
		if(e.getDamager() instanceof Player){
			UHCPlayer gamePlayer = UHCPlayer.get(e.getDamager().getUniqueId());
			if(gamePlayer.isInArena()){
				return;
			}
			if(gamePlayer.isSpectator()){
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void drop(PlayerDropItemEvent e) {
		UHCPlayer gamePlayer = UHCPlayer.get(e.getPlayer().getUniqueId());
		if (gamePlayer.isSpectator()) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void blockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		UHCPlayer gamePlayer = UHCPlayer.get(p.getUniqueId());
		if (gamePlayer.isSpectator()) {
			e.setCancelled(true);
			p.sendMessage(ChatUtils.message("&cYou can't break items while in spectator mode!"));
		}
	}

	@EventHandler
	public void blockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		UHCPlayer gamePlayer = UHCPlayer.get(p.getUniqueId());
		if (gamePlayer.isSpectator()){
			e.setCancelled(true);
			p.sendMessage(ChatUtils.message("&cYou can't place items while in spectator mode!"));
		}
	}

	@EventHandler
	public void leave(PlayerQuitEvent e) {
		UHCPlayer gamePlayer = UHCPlayer.get(e.getPlayer().getUniqueId());
		if (gamePlayer.isSpectator()) {
			SpecCommand.unSpec(e.getPlayer());
		}
	}

	@EventHandler
	public void interact(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		UHCPlayer gamePlayer = UHCPlayer.get(p.getUniqueId());
		Random r = ThreadLocalRandom.current();
		ArrayList<Player> players = new ArrayList<>();
        UHC.players.stream().filter(uuid -> Bukkit.getPlayer(uuid) != null).forEach(uuid -> players.add(Bukkit.getPlayer(uuid)));

		if (gamePlayer.isSpectator()) {
			if(p.getItemInHand() != null || p.getItemInHand().getType() != Material.AIR){
				//If they have something in their hand
				return;
			}
			if (e.getAction() == Action.LEFT_CLICK_AIR
					|| e.getAction() == Action.LEFT_CLICK_BLOCK && p.getItemInHand().getType() == Material.AIR) {
			    if(players.size() == 0){
			        p.sendMessage(ChatUtils.message("&cThere are not enough players in the game to do this!"));
			        return;
                }
				Player loc =  players.get(r.nextInt(players.size()));
				if(loc == null){
				    p.sendMessage(ChatUtils.message("&cThere was a problem randomly selecting a player!"));
				    return;
                }

				p.teleport(loc);
				p.sendMessage(ChatUtils.message("&6Teleported to&8: &3" + loc.getName()));
			}
		}
	}


}
