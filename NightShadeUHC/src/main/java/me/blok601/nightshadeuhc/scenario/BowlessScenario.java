package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

/**
 * Created by Blok on 1/28/2018.
 */
public class BowlessScenario extends Scenario{

    public BowlessScenario(){
        super("Bowless", "Bows can't be crafted", new ItemBuilder(Material.BOW).name("Bowless").make());
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent e){
        if(!isEnabled()){
            return;
        }

        if(e.getRecipe().getResult().getType() == Material.BOW){
            e.getRecipe().getResult().setType(Material.AIR); //crafting
        }

    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(!isEnabled()){
            return;
        }

        if(e.getInventory() == null) return;
        if(e.getWhoClicked() == null) return;
        if(e.getClick() == null) return;
        if(e.getCurrentItem() == null) return;

        if(e.getCurrentItem().getType() == Material.BOW){
            e.getCurrentItem().setType(Material.AIR); //Chests, Villagers, etc
        }
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent e){
        if(!isEnabled()){
            return;
        }

        if(!(e.getEntity() instanceof Arrow)){
           return;
        }

        if(e.getEntity().getShooter() instanceof Player){
            Player p = (Player) e.getEntity().getShooter();
            if(p.getItemInHand().getType() == Material.BOW){
                e.setCancelled(true);
                p.sendMessage(ChatUtils.message(getPrefix() + "&cYou can't use a bow in bowless!"));
            }
        }
    }

}
