package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

public class NoFurnaceScenario extends Scenario {


    public NoFurnaceScenario() {
        super("No Furnace", "Furnaces are not Craftable", new ItemBuilder(Material.FURNACE).name(ChatUtils.format("No Furnace")).make());
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
            p.sendMessage(ChatUtils.format(getPrefix() + "&fFurnaces are &cdisabled&f, did you read the matchpost?"));

        }
    }
}

