package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

public class AppleFamineScenario extends Scenario{

    public AppleFamineScenario() {
        super("Apple Famine", "Apples do not drop from trees", new ItemBuilder(Material.APPLE).name("Apple Famine").make());
    }


    @EventHandler
    public void onSpawn(ItemSpawnEvent event){
        if(!isEnabled()){
            return;
        }

        Item itemDrop = event.getEntity();
        if(itemDrop.getItemStack().getType() == Material.APPLE){
            event.setCancelled(true);
        }
    }
}
