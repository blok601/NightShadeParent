package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerFishEvent;

public class RodlessScenario extends Scenario {

    public RodlessScenario() {
        super("Rodless", "You cannot craft or use fishingrods", new ItemBuilder(Material.FISHING_ROD).name(ChatUtils.format("Rodless")).make());
    }

    @EventHandler
    public void onCraft(CraftItemEvent e){
        if (!isEnabled()) return;
        if(e.getWhoClicked() == null) return;
        if(!(e.getWhoClicked() instanceof Player)) return;
        if(e.getInventory() == null || e.getClickedInventory() == null) return;
        if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR)return;

        Player p = (Player) e.getWhoClicked();

        if(e.getRecipe().getResult().getType() == Material.FISHING_ROD){
            e.setCancelled(true);
            p.closeInventory();
            p.sendMessage(ChatUtils.format(getPrefix() + "Dog you cant craft fishing rods HELLOOOOO"));

        }
    }


    @EventHandler
    public void onFish(PlayerFishEvent e) {
        e.setCancelled(true);
    }
}
