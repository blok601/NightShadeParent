package me.blok601.nightshadeuhc.scenario;

import com.massivecraft.massivecore.util.MUtil;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.event.PvPEnableEvent;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.scenario.interfaces.StarterItems;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Blok on 2/16/2018.
 */
public class SkyhighScenario extends Scenario implements StarterItems {


    public SkyhighScenario() {
        super ("Skyhigh", "After a certain period of time, you will take damage every 30 seconds if you are below Y:100", new ItemBuilder(Material.COBBLESTONE).name("Skyhigh").make());
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

                GameManager.get().getWorld().getPlayers().stream().filter(player -> UHC.players.contains(player.getUniqueId())).filter(u -> u.getWorld().getName().equalsIgnoreCase(GameManager.get().getWorld().getName())).filter(o -> o.getLocation().getY() < 100).forEach((Consumer<Player>) uuid -> {
                    uuid.damage(0.5);
                    uuid.sendMessage(ChatUtils.format(getPrefix()+ " &eYou have lost 0.5 hearts for being under y:100!"));
                });


            }
        }.runTaskTimer(UHC.get(), 0, 20*30); //30 seconds
    }

    @Override
    public List<ItemStack> getStarterItems() {
        return MUtil.list(new ItemStack(Material.SNOW_BLOCK, 2),
                new ItemStack(Material.PUMPKIN),
                new ItemStack(Material.DIAMOND_SPADE),
                new ItemStack(Material.STAINED_CLAY, 64),
                new ItemStack(Material.STAINED_CLAY, 64),
                new ItemStack(Material.STRING, 2),
                new ItemStack(Material.FEATHER, 16));
    }
}
