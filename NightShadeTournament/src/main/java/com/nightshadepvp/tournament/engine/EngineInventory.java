package com.nightshadepvp.tournament.engine;

import com.massivecraft.massivecore.Engine;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.handler.GameHandler;
import com.nightshadepvp.tournament.entity.handler.KitHandler;
import com.nightshadepvp.tournament.entity.objects.data.Kit;
import com.nightshadepvp.tournament.utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Blok on 6/13/2018.
 */
public class EngineInventory extends Engine {

    private static EngineInventory i = new EngineInventory();
    public static EngineInventory get() {return i;}

    public static HashMap<UUID, Kit> edtingMap = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getInventory() == null) return;
        if(e.getWhoClicked() == null) return;
        if(e.getClickedInventory() == null) return;
        if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;


        if(!(e.getWhoClicked() instanceof Player)) return;

        Player p = (Player) e.getWhoClicked();
        TPlayer tPlayer = TPlayer.get(p);

        if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "Kit Selector")){
            e.setCancelled(true);
            if(edtingMap.containsKey(p.getUniqueId())){ //Check if they are ediitng a kit, if not they are settinng the game kit
                Kit kit = KitHandler.getInstance().getKit(e.getCurrentItem());
                if(kit == null){
                    p.sendMessage(ChatUtils.message("&cSomething went wrong when selecting the kit!"));
                    return;
                }

                tPlayer.editKits(kit);
                edtingMap.replace(p.getUniqueId(), kit);
                p.closeInventory();
                p.sendMessage(ChatUtils.message("&bWhen you are done editing &f" + kit.getName() + "&b, use &f/savekit&b to save the kit and return to spawn."));
                //Basically done here
            }else{
                Kit kit = KitHandler.getInstance().getKit(e.getCurrentItem());
                if(kit == null){
                    p.sendMessage(ChatUtils.message("&cSomething went wrong when selecting the kit!"));
                    return;
                }

                GameHandler.getInstance().setKit(kit);
                p.closeInventory();
                p.sendMessage(ChatUtils.message("&bSelected &f" + kit.getName() + " &bas the kit!"));
                return;
            }
        }

        if(e.getClickedInventory() instanceof PlayerInventory){
            //Clicked in their inv
            if(TPlayer.get(p).isSpectator()) e.setCancelled(true);
        }

        if(e.getClickedInventory().getName().contains("'s Inventory")){
            e.setCancelled(true); //Viewable inv
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e){
        if(e.getInventory() == null) return;
        if(e.getInventory().getName() == null) return;
        if(e.getPlayer() instanceof Player){
            Player p = (Player) e.getPlayer();
            if(e.getInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + "Kit Selector")){
                if(edtingMap.containsKey(p.getUniqueId())){ //Closed because they are editing kit -- don't need to do anything here
                    return;
                }
            }
        }
    }

}
