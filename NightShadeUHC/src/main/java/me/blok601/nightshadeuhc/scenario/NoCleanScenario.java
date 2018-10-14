package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.events.CustomDeathEvent;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Blok on 12/3/2017.
 */
public class NoCleanScenario extends Scenario{

    public NoCleanScenario(){
        super ("NoClean", "When you kill someone you get 30 seconds of invicibility", new ItemBuilder(Material.IRON_BARDING).name("NoClean").make());
    }

    @EventHandler
    public void onDeath(CustomDeathEvent e){

        if(!isEnabled()){
            return;
        }

        if(e.isCancelled()) return;

        if(e.getKiller() == null) return;

        Player killer = e.getKiller();
        UHCPlayer killerGP = UHCPlayer.get(killer.getUniqueId());
        killerGP.setNoClean(true);
        killerGP.setNoCleanTimer(30);
        new BukkitRunnable(){
            @Override
            public void run() {
                if(!killerGP.isNoClean()){
                    this.cancel();
                    return;
                }

                if(killerGP.getNoCleanTimer() == 0){
                    killer.sendMessage(ChatUtils.format(getPrefix() + "&eYour NoClean timer has worn off! You are no longer invincible!"));
                    killerGP.setNoClean(false);
                    this.cancel();
                    return;
                }
                killerGP.setNoCleanTimer(killerGP.getNoCleanTimer()-1);
            }
        }.runTaskTimer(UHC.get(), 0, 20);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){

        if(!isEnabled()){
            return;
        }

        if(!(e.getDamager() instanceof Player)) return;
        if(!(e.getEntity() instanceof Player)) return;
        if(e.getDamage() == 0) return;
        if(e.isCancelled()) return;

        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            UHCPlayer gamePlayer = UHCPlayer.get(p.getUniqueId());
            if(gamePlayer.isNoClean()){ //They have a timer
                e.setCancelled(true);
            }
        }

        if(e.getDamager() instanceof Player){
            Player p = (Player) e.getDamager();
            UHCPlayer gamePlayer = UHCPlayer.get(p.getUniqueId());
            if(gamePlayer.isNoClean()){
                gamePlayer.setNoClean(false);
                p.sendMessage(ChatUtils.format("&4NoClean&8» &eYou attacked another player so your invincibility wore off!"));
            }
        }
    }
  @EventHandler
  public void onCombust(EntityCombustEvent e) {
    if (!isEnabled()) return;
    if (e.getEntity() instanceof Player) {
      Player p = (Player) e.getEntity();
      UHCPlayer gamePlayer = UHCPlayer.get(p.getUniqueId());
      if (gamePlayer.isNoClean()) { //They have a timer
        e.setCancelled(true);

      }
    }
  }
  @EventHandler
  public void onDamage1(EntityDamageEvent e) {
    if (!isEnabled()) return;
    if (e.getEntity() instanceof Player) {
      Player p = (Player) e.getEntity();
      UHCPlayer gamePlayer = UHCPlayer.get(p.getUniqueId());
      if (gamePlayer.isNoClean()) { //They have a timer
        if (e.getCause() == DamageCause.LAVA || e.getCause() == DamageCause.FIRE || e.getCause() == DamageCause.FIRE_TICK) {
          e.setCancelled(true);
        }
      }
    }
  }
}
