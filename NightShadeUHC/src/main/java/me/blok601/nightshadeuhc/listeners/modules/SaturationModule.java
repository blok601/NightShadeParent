package me.blok601.nightshadeuhc.listeners.modules;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

/**
 * Created by Blok on 12/29/2017.
 */
public class SaturationModule extends Component{


    public SaturationModule() {
        super("Saturation Fix", new ItemBuilder(Material.COOKED_BEEF).name("&eSaturation Fix").make(), true);
    }

    @EventHandler
    public void on(PlayerItemConsumeEvent e){
       if(!isEnabled()){
           return;
       }

       Player p = e. getPlayer();
       float before = p.getSaturation();

       new BukkitRunnable(){
           @Override
           public void run() {
               double change = p.getSaturation() - before;

               p.setSaturation((float)  (before + change * 2.5));
           }
       }.runTaskLater(UHC.get(), 1);
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent e){

        if(!(e.getEntity() instanceof Player)) return;

        Player p = (Player) e.getEntity();

        if(e.getFoodLevel() < p.getFoodLevel()){
            e.setCancelled(new Random().nextInt(100) < 60);
        }

    }
}
