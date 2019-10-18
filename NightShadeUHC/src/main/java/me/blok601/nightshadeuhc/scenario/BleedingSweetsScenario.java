package me.blok601.nightshadeuhc.scenario;

import com.nightshadepvp.core.utils.ChatUtils;
import com.nightshadepvp.core.utils.ItemBuilder;
import me.blok601.nightshadeuhc.event.CustomDeathEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class BleedingSweetsScenario extends Scenario{

    public BleedingSweetsScenario() {
        super("Bleeding Sweets", "When a player dies, they drop 1 diamond, 5 gold, 16 arrows and 1 string.", new ItemBuilder(Material.CAKE).name(ChatUtils.format("Bleeding Sweets")).make());
    }

    @EventHandler
    public void onDeath(CustomDeathEvent e){
        if(!isEnabled()){
            return;
        }

        ItemStack diamond = new ItemStack(Material.DIAMOND, 1);
        ItemStack gold = new ItemStack(Material.GOLD_INGOT, 5);
        ItemStack arrows = new ItemStack(Material.ARROW, 16);
        ItemStack string = new ItemStack(Material.STRING, 1);
        ItemStack book = new ItemStack(Material.BOOK, 1);

        e.getItems().add(diamond);
        e.getItems().add(gold);
        e.getItems().add(arrows);
        e.getItems().add(string);
        e.getItems().add(book);
}
}
