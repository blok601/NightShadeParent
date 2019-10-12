package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Blok on 6/24/2018.
 */
public class BenchBlitzScenario extends Scenario{

    private Set<UUID> crafted;

    public BenchBlitzScenario() {
        super("BenchBlitz", "You can only craft one crafting table", new ItemBuilder(Material.WORKBENCH).name("BenchBlitz").make());
        crafted = new HashSet<>();
    }

    @EventHandler
    public void onCraft(CraftItemEvent e){
        if(!isEnabled()) return;

        if(e.getWhoClicked() == null) return;
        if(!(e.getWhoClicked() instanceof Player)) return;
        if(e.getInventory() == null || e.getClickedInventory() == null) return;
        if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR)return;

        Player p = (Player) e.getWhoClicked();

        if(e.getRecipe().getResult().getType() == Material.WORKBENCH){
            if (e.isShiftClick()) {
                e.setCancelled(true);
                p.closeInventory();
                p.sendMessage(ChatUtils.format(getPrefix() + "You cannot shift click crafting tables in bench blitz!"));+
                return;
            }
            if(crafted.contains(p.getUniqueId())){
                e.setCancelled(true);
                p.closeInventory();
                p.sendMessage(ChatUtils.format(getPrefix() + "You have already crafted a crafting table this game!"));
                return;
            }

            crafted.add(p.getUniqueId());
            p.sendMessage(ChatUtils.format(getPrefix() + "&eYou have crafted your one and only crafting table..."));
        }
    }
}
