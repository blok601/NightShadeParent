package me.blok601.nightshadeuhc.teams;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class FriendlyFire implements Listener{
	
	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player damaged = (Player) e.getEntity(), damager = null;
			
			if (e.getDamager() instanceof Player) {
				damager = (Player) e.getDamager();
			} else if (e.getDamager() instanceof Projectile) {
				if (((Projectile) e.getDamager()).getShooter() instanceof Player) {
					damager = (Player) ((Projectile) e.getDamager()).getShooter();
				}
			}
			
			if(TeamManager.getInstance().getTeam(damaged) == null || TeamManager.getInstance().getTeam(damaged) == null){
				return;
			}
			
			if (damager != null) {
				Team a = TeamManager.getInstance().getTeam(damaged), b = TeamManager.getInstance().getTeam(damager);
				
				if (a.equals(b)) {
					if(!TeamManager.getInstance().isTeamFriendlyFire()) e.setCancelled(true);
				}
			}
		}
	}
}
