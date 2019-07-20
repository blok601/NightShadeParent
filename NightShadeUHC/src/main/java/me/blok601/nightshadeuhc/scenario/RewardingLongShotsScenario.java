package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import me.blok601.nightshadeuhc.util.MathUtils;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 7/29/2017.
 */
public class RewardingLongShotsScenario extends Scenario{


    public RewardingLongShotsScenario() {
        super("RewardingLongShots", "Players get rewarded for long bow shots!", new ItemBuilder(Material.BOW).name("Rewarding Longshots").make());
    }

    @EventHandler
    public void onShoot(EntityDamageByEntityEvent e){

        if(!isEnabled()){
            return;
        }

        Entity en = e.getEntity();
        if(en instanceof Player){
            Player p = (Player) en;
            if(e.getDamager() instanceof Arrow){
                Arrow a = (Arrow) e.getDamager();
                if(a.getShooter() instanceof Player){
                    Player shooter = (Player) a.getShooter();
                    int distance = (int) shooter.getLocation().distance(p.getLocation());
                    if(MathUtils.isBetween(49, 30, distance)){
                        shooter.getInventory().addItem(new ItemStack(Material.IRON_INGOT));
                        shooter.sendMessage(ChatUtils.format(getPrefix() + "&eYou have gotten a longshot of &6" + distance + "&e! Your reward is &61 iron ingot&e."));
                    }

                    if(MathUtils.isBetween(99, 50, distance)){
                        shooter.getInventory().addItem(new ItemStack(Material.IRON_INGOT));
                        shooter.getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
                        shooter.sendMessage(ChatUtils.format(getPrefix() + "&eYou have gotten a longshot of &6" + distance + "&e! Your reward is &61 gold ingot &eand &61 gold ingot&e."));
                    }

                    if(MathUtils.isBetween(199, 100, distance)){
                        shooter.getInventory().addItem(new ItemStack(Material.IRON_INGOT));
                        shooter.getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
                        shooter.getInventory().addItem(new ItemStack(Material.DIAMOND));
                        shooter.sendMessage(ChatUtils.format(getPrefix() + "&eYou have gotten a longshot of &6" + distance + "&e! Your reward is &61 iron ingot&e, &61 gold ingot&e, and &61 diamond&e."));
                    }

                    if(distance > 200){
                        shooter.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 2));
                        shooter.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, 3));
                        shooter.getInventory().addItem(new ItemStack(Material.DIAMOND, 5));
                        shooter.sendMessage(ChatUtils.format(getPrefix() + "&eYou have gotten a longshot of &6" + distance + "&e! Your reward is &62 iron ingots&e, &63 gold ingots&e, and &65 diamonds&e."));
                    }
                }
            }
        }
    }
}
