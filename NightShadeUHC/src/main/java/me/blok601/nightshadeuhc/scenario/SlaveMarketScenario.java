package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.event.GameStartEvent;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class SlaveMarketScenario extends Scenario {
    private GameManager gameManager;
    public static HashMap<UUID, Integer> owners;


    public SlaveMarketScenario(GameManager gameManager) {
        super("Slave Market", "Slave owners bid on their teammates", new ItemBuilder(Material.DIAMOND).name("SlaveMarket").make());
        this.gameManager = gameManager;
        owners = new HashMap<>();
    }

    public Player topBidder = null;

    public Player Slave = null;

    public void onStart (GameStartEvent e) {
        if (!isEnabled()) return;
        SlaveMarketScenario.owners.forEach((k, v) -> {

            Player o = Bukkit.getPlayer(k);
            o.getInventory().addItem(new ItemStack(Material.DIAMOND, v));
        });
    }
}