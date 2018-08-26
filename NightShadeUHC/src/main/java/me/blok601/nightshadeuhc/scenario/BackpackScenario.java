package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.utils.ItemBuilder;
import me.blok601.nightshadeuhc.events.GameStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Master on 7/14/2017.
 */
public class BackpackScenario extends Scenario{

    public static HashMap<UUID, Inventory> bps = new HashMap<>();

    public BackpackScenario() {
        super("BackPack", "Each player gets their very own backpack.", new ItemBuilder(Material.WOOD).name("BackPacks").make());
    }

    @EventHandler
    public void onStart(GameStartEvent e){
        for (Player player : Bukkit.getOnlinePlayers()){
            bps.put(player.getUniqueId(), Bukkit.createInventory(null, 54, player.getName() + "'s BackPack"));
        }
    }
}
