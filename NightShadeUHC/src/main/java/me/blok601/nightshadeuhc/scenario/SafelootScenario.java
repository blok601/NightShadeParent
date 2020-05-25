package me.blok601.nightshadeuhc.scenario;

import com.google.common.collect.Maps;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SafelootScenario extends Scenario{

    private HashMap<Chest, List<String>> canOpen;
    public SafelootScenario() {
        super("Safeloot", "Only the killer and their team can access the timebomb chest", new ItemBuilder(Material.CHEST).name("Safeloot").make());
        this.canOpen = Maps.newHashMap();
    }

    @Override
    public void onToggle(boolean newState, Player player) {
        if(newState){
            if(!scenarioManager.getScen("Timebomb").isEnabled()){
                setEnabled(false);
                sendMessage(player, "&cYou can't enable Safeloot unless Timebomb is enabled!");
                return;
            }
        }
    }

    @EventHandler
    public void onOpen(PlayerInteractEvent e){
        if(!isEnabled()) return;
        Player player = e.getPlayer();
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_AIR){
            Block block = e.getClickedBlock();
            if(block.getType() != Material.CHEST) return;
            Chest chest = (Chest) block.getState();
            if(!this.canOpen.containsKey(chest)) return;

            if(!this.canOpen.get(chest).contains(player.getName())){
                e.setCancelled(true);
                sendMessage(player, "&cThis chest is protected!");
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        if(!isEnabled()) return;
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if(block.getType() != Material.CHEST) return;
        Chest chest = (Chest) block.getState();
        if(!this.canOpen.containsKey(chest)) return;
        if(!this.canOpen.get(chest).contains(player.getName())){
            event.setCancelled(true);
            sendMessage(player, "&cThis chest is protected!");
        }
    }

    public HashMap<Chest, List<String>> getCanOpen() {
        return canOpen;
    }
}
