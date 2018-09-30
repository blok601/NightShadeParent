package me.blok601.nightshadeuhc.listeners.modules;

import me.blok601.nightshadeuhc.GameState;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class CobbleComponent extends Component{


    public CobbleComponent() {
        super("1.8 Stone", new ItemBuilder(Material.COBBLESTONE).name("&e1.8 Stone").make(), true);
    }

    @EventHandler
    public void onBreak(org.bukkit.event.block.BlockBreakEvent e){
        if(isEnabled()){
            return;
        }

        if(!GameState.gameHasStarted()){
            e.setCancelled(true);
            return;
        }



        if(e.getBlock().getType() == Material.STONE){ //All 1.8 stones are considered stone and have mat data
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.COBBLESTONE));
        }
    }
}
