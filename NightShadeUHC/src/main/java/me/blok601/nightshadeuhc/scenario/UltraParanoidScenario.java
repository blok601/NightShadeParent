package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Blok on 1/28/2018.
 */
public class UltraParanoidScenario extends Scenario {


    public UltraParanoidScenario() {
        super("UltraParanoid", "Your coordinates are broadcasted every time you mine gold or diamonds", new ItemBuilder(Material.DIAMOND_PICKAXE).name("Ultra Paranoid").make());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent e){

        if(!isEnabled()){
            return;
        }

        if(e.getBlock().getType() == Material.DIAMOND_ORE){
            Location loc = e.getPlayer().getLocation();
            int x = (int) loc.getX();
            int y = (int) loc.getY();
            int z = (int) loc.getZ();
            Bukkit.broadcastMessage(ChatUtils.format(getPrefix()+ "&e" + e.getPlayer().getName() + " &7mined &bdiamonds! &8(&e" +x + "," + y + "," + z + "&8)"));
        }


        if(e.getBlock().getType() == Material.GOLD_ORE){
            Location loc = e.getPlayer().getLocation();
            int x = (int) loc.getX();
            int y = (int) loc.getY();
            int z = (int) loc.getZ();
            Bukkit.broadcastMessage(ChatUtils.format(getPrefix()+ "&e" + e.getPlayer().getName() + " &7mined &6gold! &8(&e" +x + "," + y + "," + z + "&8)"));
        }
    }
}
