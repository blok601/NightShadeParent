package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.events.GameStartEvent;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class PuppyPlusScenario extends Scenario{
    int typeID = 95;


    public PuppyPlusScenario() {
        super("Puppy Power +", "Everyone starts with 3 stacks of Bones, 3 stacks of Rotten Flesh, and 3 stacks of Wolf Spawn Eggs\n", new ItemBuilder(Material.RED_ROSE).name("Puppy Power +").make());
    }

    @EventHandler
    public void onStart(GameStartEvent e){
        if(!isEnabled()){
            return;
        }

        Bukkit.getOnlinePlayers().stream().filter(o -> !UHCPlayer.get(o.getUniqueId()).isSpectator()).forEach(player ->{
            player.getInventory().addItem(new ItemStack(Material.BONE, 192));
            player.getInventory().addItem(new ItemStack(Material.ROTTEN_FLESH, 192));
            player.getInventory().addItem(new ItemStack(Material.MONSTER_EGG, 192, (short) typeID));
        });
    }

}
