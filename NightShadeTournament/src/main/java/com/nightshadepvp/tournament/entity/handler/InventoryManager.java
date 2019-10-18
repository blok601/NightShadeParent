package com.nightshadepvp.tournament.entity.handler;

import com.nightshadepvp.tournament.Tournament;
import com.nightshadepvp.tournament.entity.objects.player.ViewableInventory;
import com.nightshadepvp.tournament.utils.InventoryUtils;
import com.nightshadepvp.tournament.utils.NumberUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Blok on 8/10/2017.
 */
public class InventoryManager {
    private static InventoryManager ourInstance = new InventoryManager();

    public static InventoryManager getInstance() {
        return ourInstance;
    }

    private InventoryManager() {
    }

    private HashMap<UUID, ViewableInventory> inventories = new HashMap<>();

    public void addInventory(final Player player){
        List<String> effects = new ArrayList<>();
        for (PotionEffect ef : player.getActivePotionEffects()){
            effects.add(ef.getType().getName() + " " + ef.getAmplifier()+1 + " (" + NumberUtils.formatSecs(ef.getDuration() / 20) + ")");
        }

        inventories.put(player.getUniqueId(), new ViewableInventory(player.getName(), Math.round(player.getHealth()), Math.round(player.getFoodLevel()), effects, InventoryUtils.playerInventoryFromPlayer(player)));
        new BukkitRunnable(){
            @Override
            public void run() {
                inventories.remove(player.getUniqueId());
            }
        }.runTaskLater(Tournament.get(), 20*60);

    }

    public ViewableInventory getInventory(Player target){
        return this.inventories.getOrDefault(target.getUniqueId(), null);

    }

    public boolean hasViewableInventory(Player target){
        return inventories.containsKey(target.getUniqueId());
    }
}
