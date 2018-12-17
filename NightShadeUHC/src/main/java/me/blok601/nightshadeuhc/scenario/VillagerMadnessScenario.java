package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.event.GameStartEvent;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 2/10/2018.
 */
public class VillagerMadnessScenario extends Scenario{
    int typeID = 120;


    public VillagerMadnessScenario() {
        super("Villager Madness", "At the start of the game every player receives a stack of emerald blocks to trade with villagers", new ItemBuilder(Material.EMERALD_BLOCK).name("Villager Madness").make());
    }

    @EventHandler
    public void onStart(GameStartEvent e){
        if(!isEnabled()){
            return;
        }

        Bukkit.getOnlinePlayers().stream().filter(o -> !UHCPlayer.get(o.getUniqueId()).isSpectator()).forEach(player ->{
            player.getInventory().addItem(new ItemStack(Material.EMERALD_BLOCK, 64));
            player.getInventory().addItem(new ItemStack(Material.MONSTER_EGG, 64, (short) typeID));
        });
    }

}
