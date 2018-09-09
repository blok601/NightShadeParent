package me.blok601.nightshadeuhc.staff.listener;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.staff.spec.SpecCommand;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.UUID;

/**
 * Created by Blok on 6/19/2017.
 */
public class StaffListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        UHCPlayer uhcPlayer = UHCPlayer.get(p);
        if(uhcPlayer.isStaffMode()){

            if(e.getItem() == null){
                return;
            }

            ItemStack item  = e.getItem();
            if(item.getType() == null){
                return;
            }


            if(item.getType() == Material.AIR){
                return;
            }


            if(item.getType() == Material.TORCH){
                //Toggle Vanish
                if(uhcPlayer.isVanished()){
                    uhcPlayer.unVanish();
                    p.sendMessage(ChatUtils.message("&eYou are now &avisible!"));
                }else{
                    uhcPlayer.vanish(false);
                    p.sendMessage(ChatUtils.message("&eYou are now &avanished!"));
                }

                return;

            }

            if (item.getType() == Material.WATCH) {
                if (UHC.players.size() == 0) {
                    p.sendMessage(ChatUtils.message("&cThere are not enough players online to do this!"));
                    return;
                }
                Random random = new Random();
                //Player rand = Bukkit.getPlayer(UHC.players.get(random.nextInt(UHC.players.size())));

                int element = new Random().nextInt(UHC.players.size());
                int i = 0;
                Player rand = null;
                for (UUID uuid : UHC.players) {
                    if (i == element) {
                        rand = Bukkit.getPlayer(uuid);
                        if (rand == null) break;
                    }
                    i++;
                }

                if (rand == null) {
                    p.sendMessage(ChatUtils.message("&cThere was a problem trying to find a random player!"));
                    return;
                }

                p.teleport(rand.getLocation());
                p.sendMessage(ChatUtils.message("&aTeleported to " + rand.getName()));
            }
        }
    }

    @EventHandler
    public void onEClick(PlayerInteractAtEntityEvent e){
        Player p = e.getPlayer();
        Entity entity = e.getRightClicked();
        if(!(entity instanceof  Player)){
            return;
        }

        UHCPlayer uhcPlayer = UHCPlayer.get(p);

        Player target = (Player) entity;
        if(uhcPlayer.isStaffMode()) {
            ItemStack item = p.getItemInHand();
            if (item.getType() == Material.BOOK) {
                Inventory inv = Bukkit.createInventory(null, 54, ChatUtils.format("&6" + target.getName() + "'s Inventory"));

                for (ItemStack stack : target.getInventory()) {
                    if (stack == null) {
                        continue;
                    }

                    inv.addItem(stack);
                }


                p.sendMessage(ChatUtils.message("&6Opening " + target.getName() + "'s Inventory..."));
                p.openInventory(inv);

            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getInventory() == null){
            return;
        }

        if(e.getClickedInventory() == null){
            return;
        }

        if(e.getWhoClicked() == null){
            return;
        }

        if(e.getCurrentItem() == null){
            return;
        }

        if(e.getClickedInventory().getName().equalsIgnoreCase("Alert Toggles")){
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            NSPlayer user= NSPlayer.get(player.getUniqueId());
            UHCPlayer uhcPlayer = UHCPlayer.get(player);
            if(user.getRank().getValue() >= Rank.TRIAL.getValue()){
                if (e.getCurrentItem().getType() == Material.TORCH) { //Spec info
                    if(uhcPlayer.isReceivingSpectatorInfo()){
                        uhcPlayer.setReceivingSpectatorInfo(false);
                        player.closeInventory();
                        player.sendMessage(ChatUtils.message("&eYou are no longer receiving spectator info alerts."));
                    }else{
                        uhcPlayer.setReceivingSpectatorInfo(true);
                        player.closeInventory();
                        player.sendMessage(ChatUtils.message("&eYou are now receiving spectator info alerts."));
                    }
                } else if (e.getCurrentItem().getType() == Material.DIAMOND_ORE) { //Mining Alerts
                    if(uhcPlayer.isReceivingMiningAlerts()){
                        uhcPlayer.setReceivingMiningAlerts(false);
                        player.closeInventory();
                        player.sendMessage(ChatUtils.message("&eYou are no longer receiving mining alerts."));
                    }else{
                        uhcPlayer.setReceivingMiningAlerts(true);
                        player.closeInventory();
                        player.sendMessage(ChatUtils.message("&eYou are now receiving mining alerts."));
                    }
                } else if (e.getCurrentItem().getType() == Material.SIGN) { //ToggleSneak alerts
                    if (user.isReceivingToggleSneak()) {
                        user.setReceivingToggleSneak(false);
                        player.closeInventory();
                        player.sendMessage(ChatUtils.message("&eYou are no longer receiving sneak alerts"));
                        return;
                    }

                    user.setReceivingToggleSneak(false);
                    player.sendMessage(ChatUtils.message("&eYou are now receiving toggle sneak alerts."));
                } else if (e.getCurrentItem().getType() == Material.PAPER) { //Staff chat
                    if (user.isReceivingStaffChat()) {
                        user.setReceivingStaffChat(false);
                        player.closeInventory();
                        player.sendMessage(ChatUtils.message("&eYou are no longer receiving staff chat."));
                        return;
                    }

                    user.setReceivingStaffChat(true);
                    player.sendMessage(ChatUtils.message("&eYou are now receiving staff chat."));
                } else if (e.getCurrentItem().getType() == Material.COMPASS) {
                    if (uhcPlayer.isReceivingCommandSpy()) {
                        uhcPlayer.setReceivingCommandSpy(false);
                        player.closeInventory();
                        player.sendMessage(ChatUtils.message("&eYou are no longer receiving command spy."));
                        return;
                    }
                    uhcPlayer.setReceivingCommandSpy(true);
                    player.sendMessage(ChatUtils.message("&eYou are now receiving command spy."));
                }else if(e.getCurrentItem().getType() == Material.DIAMOND_SWORD){
                    player.closeInventory();
                    player.sendMessage(ChatUtils.message("&cThis feature has not been enabled. Tell Blok to get to work!"));
                }
            }else{
                player.closeInventory();
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        UHCPlayer uhcPlayer = UHCPlayer.get(p);
        if (uhcPlayer.isStaffMode()) {
            SpecCommand.unSpec(p);
            Bukkit.getOnlinePlayers().forEach(o -> o.showPlayer(p));
            p.chat("/rea");
            p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
            uhcPlayer.setStaffMode(false);
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);
        }
    }
}
