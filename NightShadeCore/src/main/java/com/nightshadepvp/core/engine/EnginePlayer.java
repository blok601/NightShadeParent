package com.nightshadepvp.core.engine;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.massivecraft.massivecore.Engine;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.NSPlayerColl;
import com.nightshadepvp.core.events.RankChangeEvent;
import com.nightshadepvp.core.fanciful.FancyMessage;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class EnginePlayer extends Engine {

    private static EnginePlayer i = new EnginePlayer();

    public static EnginePlayer get() { return i; }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        NSPlayer user = NSPlayer.get(p.getUniqueId());
        user.setCurrentAFKTime(0);
        if(user.isAFK()){
            user.incrementTotalAFKTime(user.getCurrentAFKTime());
            user.setCurrentAFKTime(0);
            user.setAFK(false);
        }
        if(user.isInStaffChat()){
            e.setCancelled(true);
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF(p.getName());
            //out.writeUTF(MConf.get().getServerName());
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

        if(!(e.getInventory() instanceof PlayerInventory)){
            return; //Make it so it only works on their own inventory
        }

        NSPlayer nsPlayer = NSPlayer.get(p);
        nsPlayer.setCurrentAFKTime(0);
        if(nsPlayer.isAFK()){
            nsPlayer.incrementTotalAFKTime(nsPlayer.getCurrentAFKTime());
            nsPlayer.setCurrentAFKTime(0);
            nsPlayer.setAFK(false);
        }
        nsPlayer.incrementToggleSneak();
        FancyMessage fancyMessage = new FancyMessage();
        fancyMessage.command("/tp " + p.getName());
        if (nsPlayer.getToggleSneakVL() >= 15) {
            fancyMessage.text(ChatUtils.format("&4" + p.getName() + " &cmay be using ToggleSneak! &8[&4" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cInventory&8)"));
        } else if (nsPlayer.getToggleSneakVL() >= 10) {
            fancyMessage.text(ChatUtils.format("&c" + p.getName() + " &cmay be using ToggleSneak! &8[&c" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cInventory&8)"));
        } else if (nsPlayer.getToggleSneakVL() >= 5) {
            fancyMessage.text(ChatUtils.format("&6" + p.getName() + " &cmay be using ToggleSneak! &8[&c" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cInventory&8)"));
        } else if (nsPlayer.getToggleSneakVL() >= 1) {
            fancyMessage.text(ChatUtils.format("&a" + p.getName() + " &cmay be using ToggleSneak! &8[&a" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cInventory&8)"));
        }
        NSPlayerColl.get().getAllPlayerStaffOnline().stream().filter(NSPlayer::isReceivingToggleSneak).forEach(nsPlayer1 -> {
            fancyMessage.send(nsPlayer1.getPlayer());
        });
    }

    @EventHandler
    public void onToggleSneakChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();

        if(!p.isSneaking()) return;
        if(p.getGameMode() != GameMode.SURVIVAL) return;

        NSPlayer nsPlayer = NSPlayer.get(p);
        nsPlayer.setCurrentAFKTime(0);
        if(nsPlayer.isAFK()){
            nsPlayer.incrementTotalAFKTime(nsPlayer.getCurrentAFKTime());
            nsPlayer.setCurrentAFKTime(0);
            nsPlayer.setAFK(false);
        }
        nsPlayer.incrementToggleSneak();
        NSPlayerColl.get().getAllPlayerStaffOnline().stream().filter(NSPlayer::isReceivingToggleSneak).forEach(nsPlayer1 -> {
            if(nsPlayer.getToggleSneakVL() >= 15){
                nsPlayer1.msg(ChatUtils.format("&4" + p.getName() + " &cmay be using ToggleSneak! &8[&4" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cChat&8)"));
            }else if(nsPlayer.getToggleSneakVL() >= 10){
                nsPlayer1.msg(ChatUtils.format("&c" + p.getName() + " &cmay be using ToggleSneak! &8[&c" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cChat&8)"));
            }else if(nsPlayer.getToggleSneakVL() >= 5){
                nsPlayer1.msg(ChatUtils.format("&6" + p.getName() + " &cmay be using ToggleSneak! &8[&c" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cChat&8)"));
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
        nsPlayer.setCurrentAFKTime(0);
        if(nsPlayer.isAFK()){
            nsPlayer.incrementTotalAFKTime(nsPlayer.getCurrentAFKTime());
            nsPlayer.setCurrentAFKTime(0);
            nsPlayer.setAFK(false);
        }
        nsPlayer.incrementToggleSneak();
        NSPlayerColl.get().getAllPlayerStaffOnline().stream().filter(NSPlayer::isReceivingToggleSneak).forEach(nsPlayer1 -> {
            if(nsPlayer.getToggleSneakVL() >= 15){
                nsPlayer1.msg(ChatUtils.format("&4" + p.getName() + " &cmay be using ToggleSneak! &8[&4" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cInventory&8)"));
            }else if(nsPlayer.getToggleSneakVL() >= 10){
                nsPlayer1.msg(ChatUtils.format("&c" + p.getName() + " &cmay be using ToggleSneak! &8[&c" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cInventory&8)"));
            }else if(nsPlayer.getToggleSneakVL() >= 5){
                nsPlayer1.msg(ChatUtils.format("&6" + p.getName() + " &cmay be using ToggleSneak! &8[&c" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cInventory&8)"));
            }else if(nsPlayer.getToggleSneakVL() >= 1){
                nsPlayer1.msg(ChatUtils.format("&a" + p.getName() + " &cmay be using ToggleSneak! &8[&a" + nsPlayer.getToggleSneakVL() + "&8] &8(&eType&8: &cInventory&8)"));
            }
        });
    }

    @EventHandler
    public void onDisabledCommand(PlayerCommandPreprocessEvent e) {
        NSPlayer user = NSPlayer.get(e.getPlayer());
        user.setCurrentAFKTime(0);
        if(user.isAFK()){
            user.incrementTotalAFKTime(user.getCurrentAFKTime());
            user.setCurrentAFKTime(0);
            user.setAFK(false);
        }
        if (e.getMessage().startsWith("//calc") || e.getMessage().startsWith("//calculate")) {
            NSPlayer nsPlayer = NSPlayer.get(e.getPlayer());
            if (nsPlayer.hasRank(Rank.ADMIN)) {
                nsPlayer.msg(ChatUtils.message("&4No need to do that command..."));
                nsPlayer.msg(ChatUtils.message("&4Please stop trying to exploit bugs. This incident has been reported to the moderators."));
                NSPlayerColl.get().getAllPlayerStaffOnline().stream().forEach(nsPlayer1 -> {
                    nsPlayer1.msg(ChatUtils.message("&e" + nsPlayer.getName() + " &4tried to exploit the //calc bug!"));
                });
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        Player p = e.getPlayer();

        if (e.getFrom().getX() == e.getTo().getX() && e.getFrom().getZ() == e.getTo().getZ()) return;

        NSPlayer user = NSPlayer.get(p);
        user.setCurrentAFKTime(0);
        if(user.isAFK()){
            user.incrementTotalAFKTime(user.getCurrentAFKTime());
            user.setCurrentAFKTime(0);
            user.setAFK(false);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        new BukkitRunnable(){
            @Override
            public void run() {
                player.playSound(player.getLocation(), Sound.NOTE_BASS, 5, 5);
            }
        }.runTaskLater(Core.get(), 2L);


        if (!Core.get().getLoginTasks().containsKey(player.getUniqueId())) {
            return;
        }

        Core.get().getLoginTasks().get(player.getUniqueId()).forEach(uuidConsumer -> uuidConsumer.accept(player.getUniqueId()));
        Core.get().getLoginTasks().remove(player.getUniqueId());

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSignChange(SignChangeEvent e) {
        if (NSPlayer.get(e.getPlayer()).hasRank(Rank.Builder)) {
            for (int i = 0; i <= 3; ++i) {
                String line = e.getLine(i);
                line = ChatUtils.format(line);
                e.setLine(i, line);
            }
        }
    }

    @EventHandler
    public void onRankChange(RankChangeEvent event){
        UUID uuid = event.getPlayerUUID();
        String name;
        if(Bukkit.getPlayer(uuid) != null){
            name = Bukkit.getPlayer(uuid).getName();
        }else{
            name = Bukkit.getOfflinePlayer(uuid).getName();
        }

        if(name == null) return;

        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + name + " group set " + event.getToRank().getPexRank());

    }
}
