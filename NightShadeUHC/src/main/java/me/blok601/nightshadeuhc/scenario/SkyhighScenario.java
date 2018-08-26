package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.events.GameStartEvent;
import me.blok601.nightshadeuhc.events.PvPEnableEvent;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Consumer;

/**
 * Created by Blok on 2/16/2018.
 */
public class SkyhighScenario extends Scenario{


    public SkyhighScenario() {
        super ("Skyhigh", "After a certain period of time, you will take damage every 30 seconds if you are below Y:100", new ItemBuilder(Material.COBBLESTONE).name("Skyhigh").make());


    }

    @EventHandler
    public void on(GameStartEvent e){
        if(!isEnabled()){
            return;
        }

        Bukkit.getOnlinePlayers().stream().filter(o -> !UHCPlayer.get(o.getUniqueId()).isSpectator()).forEach(player ->{
            player.getInventory().addItem(new ItemStack(Material.SNOW_BLOCK, 2));
            player.getInventory().addItem(new ItemStack(Material.PUMPKIN,1));
            player.getInventory().addItem(new ItemStack(Material.DIAMOND_SPADE, 1));
            player.getInventory().addItem(new ItemStack(Material.STAINED_CLAY, 64));
            player.getInventory().addItem(new ItemStack(Material.STAINED_CLAY, 64));
            player.getInventory().addItem(new ItemStack(Material.STRING, 2));
            player.getInventory().addItem(new ItemStack(Material.FEATHER, 16));
            player.sendMessage(ChatUtils.format(getPrefix()+ " &eYou have received your Skyhigh Items!"));


        });
    }


    @EventHandler
    public void on(PvPEnableEvent e){
        new BukkitRunnable(){
            @Override
            public void run() {
                if(!isEnabled()){
                    this.cancel();
                    return;
                }

                GameManager.getWorld().getPlayers().stream().filter(player -> UHC.players.contains(player.getUniqueId())).filter(u -> u.getWorld().getName().equalsIgnoreCase(GameManager.getWorld().getName())).filter(o -> o.getLocation().getY() < 100).forEach((Consumer<Player>) uuid -> {
                    uuid.damage(0.5);
                    uuid.sendMessage(ChatUtils.format(getPrefix()+ " &eYou have lost 0.5 hearts for being under y:100!"));
                });


            }
        }.runTaskTimer(UHC.get(), 0, 20*30); //30 seconds
    }
}
