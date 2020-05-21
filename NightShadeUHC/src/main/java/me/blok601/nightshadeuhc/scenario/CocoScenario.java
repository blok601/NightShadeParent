package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.event.GameStartEvent;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class CocoScenario extends Scenario{

    public CocoScenario() {
        super("Coco", "Players start with 5 coco beans, right clicking them gives Speed I and Strength I for 30 seconds and 10 seconds. After 30 seconds, you receive 15 seconds of slowness I and 5 seconds of weakness I.", new ItemBuilder(Material.COCOA).name("Coco").make());
    }

    @EventHandler
    public void onStart(GameStartEvent event){
        if(!isEnabled()) return;


    }
}
