package com.nightshadepvp.nightcheat.cheat.movement;

import com.nightshadepvp.nightcheat.cheat.Cheat;
import com.nightshadepvp.nightcheat.cheat.CheatType;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.PlayerInventory;

public class ToggleSneakCheat extends Cheat {

    public ToggleSneakCheat() {
        super("ToggleSneak", CheatType.MOVEMENT);
        this.setViolationsToNotify(1); //for now
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(!(event.getWhoClicked() instanceof Player)){
            return;
        }

        Player p = (Player) event.getWhoClicked();

        if(!p.isSneaking()) return;

        flag(p, this, "Used an inventory while sneaking");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player p = event.getPlayer();

        if(!p.isSneaking()) return;
        if(p.getGameMode() != GameMode.SURVIVAL) return;

        flag(p, this, "Used chat while sneaking");
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent event){
        if(!(event.getPlayer() instanceof Player)){
            return;
        }

        Player p = (Player) event.getPlayer();

        if(!p.isSneaking()) return;
        if(p.getGameMode() != GameMode.SURVIVAL) return;

        if(!(event.getInventory() instanceof PlayerInventory)){
            return; //Make it so it only works on their own inventory
        }

        flag(p, this, "Opened an inventory while sneaking");
    }
}
