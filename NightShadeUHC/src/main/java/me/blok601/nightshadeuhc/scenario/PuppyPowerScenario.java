package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.events.GameStartEvent;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class PuppyPowerScenario extends Scenario{
    int typeID = 95;


    public PuppyPowerScenario() {
        super("Puppy Power", "Everyone starts with 64 Bones, 64 Rotten Flesh, and 64 Wolf Spawn Eggs", new ItemBuilder(Material.BONE).name("Puppy Power").make());
    }

    @EventHandler
    public void onStart(GameStartEvent e){
        if(!isEnabled()){
            return;
        }

        Bukkit.getOnlinePlayers().stream().filter(o -> !UHCPlayer.get(o.getUniqueId()).isSpectator()).forEach(player ->{
            player.getInventory().addItem(new ItemStack(Material.BONE, 64));
            player.getInventory().addItem(new ItemStack(Material.ROTTEN_FLESH, 64));
            player.getInventory().addItem(new ItemStack(Material.MONSTER_EGG, 64, (short) typeID));
        });
    }

}
