package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.event.CustomDeathEvent;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class FurnaceDeathScenario extends Scenario {


    public FurnaceDeathScenario() {
        super("Furnace Death", "Players drop a Furnace Upon Death and Furnaces are uncraftable!", new ItemBuilder(Material.FURNACE).name(ChatUtils.format("Furnace Death")).make());
    }

    @EventHandler
    public void onDeath(CustomDeathEvent e){
        if(!isEnabled()){
            return;
        }

        ItemStack furnace = new ItemStack(Material.FURNACE, 1);

        e.getItems().add(furnace);
    }

    @EventHandler
    public void onCraft(CraftItemEvent e){
        if (!isEnabled()) return;
        if(e.getWhoClicked() == null) return;
        if(!(e.getWhoClicked() instanceof Player)) return;
        if(e.getInventory() == null || e.getClickedInventory() == null) return;
        if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR)return;

        Player p = (Player) e.getWhoClicked();

        if(e.getRecipe().getResult().getType() == Material.FURNACE){
            e.setCancelled(true);
            p.closeInventory();
            p.sendMessage(ChatUtils.format(getPrefix() + "&fFurnaces are &cuncraftable&f, did you read the matchpost?"));

        }
    }
}