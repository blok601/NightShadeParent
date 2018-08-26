package com.nightshadepvp.core.engine;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.massivecraft.massivecore.Engine;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.MConf;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.NSPlayerColl;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class EnginePlayer extends Engine {

    private static EnginePlayer i = new EnginePlayer();
    public static EnginePlayer get() { return i; }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        NSPlayer user = NSPlayer.get(p.getUniqueId());
        if(user.isInStaffChat()){
            e.setCancelled(true);
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF(p.getName());
            out.writeUTF(MConf.get().getServerName());
            out.writeUTF(e.getMessage());
            p.sendPluginMessage(Core.get(), "staffchat", out.toByteArray());
        }
    }

    /*
    The Following are ToggleSneak checks
     */
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e){
        if(!(e.getPlayer() instanceof Player)){
           return;
        }

        Player p = (Player) e.getPlayer();

        if(!p.isSneaking()) return;
        if(p.getGameMode() != GameMode.SURVIVAL) return;

        NSPlayer nsPlayer = NSPlayer.get(p);
        nsPlayer.incrementToggleSneak();
        NSPlayerColl.get().getAllOnline().stream().filter(nsPlayer1 -> nsPlayer1.hasRank(Rank.TRIAL)).filter(NSPlayer::isReceivingToggleSneak).forEach(nsPlayer1 -> {
            if(nsPlayer.getToggleSneakVL() >= 15){
                nsPlayer1.msg(ChatUtils.format("&4" + p.getName() + " &cmay be using ToggleSneak! &8[&4" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cInventory&8)"));
            }else if(nsPlayer.getToggleSneakVL() >= 10){
                nsPlayer1.msg(ChatUtils.format("&c" + p.getName() + " &cmay be using ToggleSneak! &8[&c" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cInventory&8)"));
            }else if(nsPlayer.getToggleSneakVL() >= 5){
                nsPlayer1.msg(ChatUtils.format("&6" + p.getName() + " &ccmay be using ToggleSneak! &8[&c" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cInventory&8)"));
            }else if(nsPlayer.getToggleSneakVL() >= 1){
                nsPlayer1.msg(ChatUtils.format("&a" + p.getName() + " &cmay be using ToggleSneak! &8[&a" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cInventory&8)"));
            }
        });
    }

    @EventHandler
    public void onToggleSneakChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();

        if(!p.isSneaking()) return;
        if(p.getGameMode() != GameMode.SURVIVAL) return;

        NSPlayer nsPlayer = NSPlayer.get(p);
        nsPlayer.incrementToggleSneak();
        NSPlayerColl.get().getAllOnline().stream().filter(nsPlayer1 -> nsPlayer1.hasRank(Rank.TRIAL)).filter(NSPlayer::isReceivingToggleSneak).forEach(nsPlayer1 -> {
            if(nsPlayer.getToggleSneakVL() >= 15){
                nsPlayer1.msg(ChatUtils.format("&4" + p.getName() + " &cmay be using ToggleSneak! &8[&4" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cChat&8)"));
            }else if(nsPlayer.getToggleSneakVL() >= 10){
                nsPlayer1.msg(ChatUtils.format("&c" + p.getName() + " &cmay be using ToggleSneak! &8[&c" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cChat&8)"));
            }else if(nsPlayer.getToggleSneakVL() >= 5){
                nsPlayer1.msg(ChatUtils.format("&6" + p.getName() + " ccmay be using ToggleSneak! &8[&c" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cChat&8)"));
            }else if(nsPlayer.getToggleSneakVL() >= 1){
                nsPlayer1.msg(ChatUtils.format("&a" + p.getName() + " &cmay be using ToggleSneak! &8[&a" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cChat&8)"));
            }
        });
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(!(e.getWhoClicked() instanceof Player)){
           return;
        }

        Player p = (Player) e.getWhoClicked();

        if(!p.isSneaking()) return;

        NSPlayer nsPlayer = NSPlayer.get(p);
        nsPlayer.incrementToggleSneak();
        NSPlayerColl.get().getAllOnline().stream().filter(nsPlayer1 -> nsPlayer1.hasRank(Rank.TRIAL)).filter(NSPlayer::isReceivingToggleSneak).forEach(nsPlayer1 -> {
            if(nsPlayer.getToggleSneakVL() >= 15){
                nsPlayer1.msg(ChatUtils.format("&4" + p.getName() + " &cmay be using ToggleSneak! &8[&4" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cInventory&8)"));
            }else if(nsPlayer.getToggleSneakVL() >= 10){
                nsPlayer1.msg(ChatUtils.format("&c" + p.getName() + " &cmay be using ToggleSneak! &8[&c" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cInventory&8)"));
            }else if(nsPlayer.getToggleSneakVL() >= 5){
                nsPlayer1.msg(ChatUtils.format("&6" + p.getName() + " ccmay be using ToggleSneak! &8[&c" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cInventory&8)"));
            }else if(nsPlayer.getToggleSneakVL() >= 1){
                nsPlayer1.msg(ChatUtils.format("&a" + p.getName() + " &cmay be using ToggleSneak! &8[&a" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cInventory&8)"));
            }
        });
    }

}
