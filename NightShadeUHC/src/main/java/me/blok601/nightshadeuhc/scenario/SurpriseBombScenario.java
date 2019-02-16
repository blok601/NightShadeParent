package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.event.CustomDeathEvent;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import static com.wimbli.WorldBorder.WorldBorder.plugin;

public class SurpriseBombScenario extends Scenario{
    public SurpriseBombScenario() {
        super("Timebomb", "When someone dies, there loot is placed in a chest that explodes in a random ammount of  seconds >:)", new ItemBuilder(Material.CHEST).name("Timebomb").make());
    }

    @EventHandler
    public void onDeath(CustomDeathEvent e){
        if(!isEnabled()){
            return;
        }

        e.setDropItems(false);

        if(GameManager.get().getWorld() == null || !e.getKilled().getLocation().getWorld().getName().equalsIgnoreCase(GameManager.get().getWorld().getName())){
            return;
        }

        final Player p = e.getKilled();
        Block b = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
        final Location l = p.getLocation().clone();
        b.setType(Material.CHEST);

        Chest c = (Chest) b.getState();

        b = b.getRelative(BlockFace.NORTH);
        b.setType(Material.CHEST);

        for (ItemStack is : e.getItems()) {
            if (is == null || is.getType() == Material.AIR) {
                continue;
            }

            c.getInventory().addItem(is);
        }
        ItemStack apple = new ItemBuilder(Material.GOLDEN_APPLE).name(ChatUtils.format("&6Golden Head")).make();
        if (getScenarioManager().getScen("Golden Retriever").isEnabled()) c.getInventory().addItem(apple);

        c.getInventory().addItem(apple);

//        e.getItems().clear();
        ArmorStand armorStand = p.getWorld().spawn(c.getLocation().clone().add(0.5, 1, 0), ArmorStand.class);
        armorStand.setCustomNameVisible(true);
        armorStand.setSmall(true);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setMarker(true);


        new BukkitRunnable() {
            private int time = ((int)Math.random() * 50 ) + 10; //count down 1 more time

            @Override
            public void run() {
                time--;
                if (time == 0) {
                    l.getWorld().createExplosion(l.add(0.5, 0.5, 0.5), 5, false);
                    c.getWorld().strikeLightning(c.getLocation());
                    Bukkit.broadcastMessage(ChatUtils.format(getPrefix()+ "&6" + p.getName() + "'s corpse has exploded!"));
                    armorStand.setCustomNameVisible(false);
                    armorStand.setVisible(false);
                    armorStand.remove();
                    cancel();
                    return;
                }else if(time <= 3){
                    armorStand.setCustomName("§4" + time + "s");
                }else if(time <= 15){
                    armorStand.setCustomName("§e" + time + "s");
                }else{
                    armorStand.setCustomName("§a" + time + "s");
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }
}


