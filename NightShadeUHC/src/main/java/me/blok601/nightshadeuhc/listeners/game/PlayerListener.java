package me.blok601.nightshadeuhc.listeners.game;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.objects.PlayerTag;
import me.blok601.nightshadeuhc.GameState;
import me.blok601.nightshadeuhc.entity.MConf;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.staff.spec.SpectatorChatCommand;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Blok on 1/4/2018.
 */
public class PlayerListener implements Listener {

    @EventHandler
    public void onRegen(EntityRegainHealthEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        NSPlayer user = NSPlayer.get(p.getUniqueId());
        if (e.getBlock().getWorld().getName().equalsIgnoreCase(MConf.get().getSpawnLocation().getWorld(true))) {
            if (!user.hasRank(Rank.ADMIN)) {
                p.sendMessage(ChatUtils.message("&cOnly admins can edit spawn!"));
                e.setCancelled(true);
                return;
            }
        }

        if(e.getBlock().getWorld().getName().equalsIgnoreCase(MConf.get().getArenaLocation().getWorld(true))){
            e.setCancelled(true);
            return;
        }

        UHCPlayer gamePlayer = UHCPlayer.get(e.getPlayer().getUniqueId());
        if (gamePlayer == null) return;
        if (gamePlayer.isFrozen()) {
            e.setCancelled(true);
            p.sendMessage(ChatUtils.message("&cYou can't break blocks yet!"));
            return;

        }

        if (GameState.gameHasStarted()) {
            gamePlayer.handleBlockBreak(e.getBlock());
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        NSPlayer user = NSPlayer.get(p.getUniqueId());
        if (!e.getBlock().getWorld().getName().equalsIgnoreCase(MConf.get().getSpawnLocation().getWorld(true))) {
            return;
        }

        if (!user.hasRank(Rank.ADMIN)) {
            p.sendMessage(ChatUtils.message("&cOnly admins can edit spawn!"));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            UHCPlayer gamePlayer = UHCPlayer.get(p.getUniqueId());
            if (gamePlayer.isFrozen()) {
                e.setCancelled(true);
            }

            if(gamePlayer.isInArena()){
                if(e.getCause() == EntityDamageEvent.DamageCause.FALL){
                    e.setDamage(0);
                    return;
                }

                if(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;
                if(e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) return;

                return; //Allow it
            }

            if (!GameState.gameHasStarted()) {
                e.setCancelled(true);
                p.setHealth(p.getMaxHealth());
            }


        }
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player) {
            if (!GameState.gameHasStarted()) {
                e.setCancelled(true);
                ((Player) e.getEntity()).setFoodLevel(20);
            }
        }
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {

        if (e.getEntity().getWorld().getName().equalsIgnoreCase(MConf.get().getSpawnLocation().getWorld(true)) || e.getEntity().getWorld().getName().equalsIgnoreCase(MConf.get().getArenaLocation().getWorld(true))) {
            if (e.getEntity() instanceof Monster) {
                e.setCancelled(true);
            }
        }


    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent e) {
        if (e.isCancelled()) {
            return;
        }


        Player p = e.getPlayer();
        NSPlayer nsPlayer = NSPlayer.get(p);
        UHCPlayer uhcPlayer = UHCPlayer.get(p);

        if(e.getMessage().contains("&k") || e.getMessage().contains("&m")){
            if(nsPlayer.hasRank(Rank.TRIAL)){
                e.setCancelled(true);
                p.sendMessage(ChatUtils.message("&cNormal players can't use those characters!"));
                return;
            }
        }

        if (SpectatorChatCommand.specc.contains(p.getUniqueId())) {
            e.setCancelled(true);
            UHCPlayerColl.get().getSpectators().forEach(uhcPlayer1 -> uhcPlayer1.msg(ChatUtils.format("&8[&3SpecChat&8] &e" + p.getName() + ": &6" + e.getMessage())));
            return;
        }

        if (ChatUtils.isChatFrozen()) {
            e.setCancelled(true);
            if (!nsPlayer.hasRank(Rank.TRIAL) || uhcPlayer.isDisguised()) {
                e.setCancelled(true);
                p.sendMessage(ChatUtils.message("&cThe chat is currently frozen!"));
                return;
            }
        }

        if (uhcPlayer.isDisguised()) {
            e.setCancelled(true);
            for (Player pl : e.getRecipients()) {
                pl.sendMessage(uhcPlayer.getDisguisedName() + ": " + ChatColor.GRAY + e.getMessage());
            }
            return;
        }

        if (nsPlayer.hasRank(Rank.FRIEND)) { //Rank
            e.setCancelled(true);
            if (nsPlayer.getPrefix() != null && !nsPlayer.getPrefix().equalsIgnoreCase("")) {//Prefix
                if (nsPlayer.getCurrentTag() != PlayerTag.DEFAULT) { //Tag
                    //They have all 3 here
                    for (Player rec : e.getRecipients()) {
                        rec.sendMessage(ChatUtils.format(nsPlayer.getPrefix() + nsPlayer.getRank().getNameColor() + " " + p.getName() + " " + nsPlayer.getCurrentTag().getFormatted() + ": " + nsPlayer.getColor().getColor() + e.getMessage()));
                    }
                    return;
                }


                //Only a prefix here
                for (Player rec : e.getRecipients()){
                    rec.sendMessage(ChatUtils.format(nsPlayer.getPrefix() + nsPlayer.getRank().getNameColor() + " " + p.getName() +  ": " + nsPlayer.getColor().getColor() + e.getMessage()));
                }
                return;

            }

            //Just a rank here
            if (nsPlayer.getCurrentTag() != PlayerTag.DEFAULT) {
                for (Player rec : e.getRecipients()) {
                    rec.sendMessage(ChatUtils.format(nsPlayer.getRank().getPrefix() + nsPlayer.getRank().getNameColor() + p.getName() + " " + nsPlayer.getCurrentTag().getFormatted() + ": " + nsPlayer.getColor().getColor() + e.getMessage()));
                }
            } else {
                for (Player rec : e.getRecipients()) {
                    rec.sendMessage(ChatUtils.format(nsPlayer.getRank().getPrefix() + nsPlayer.getRank().getNameColor() + p.getName() + ": " + nsPlayer.getColor().getColor() + e.getMessage()));
                }
            }

            return;
        }

        if (nsPlayer.getPrefix() != null && !nsPlayer.getPrefix().equalsIgnoreCase("")) { //No rank but a prefix
            e.setCancelled(true);
            for (Player rec : e.getRecipients()) {
                if (nsPlayer.getCurrentTag() != PlayerTag.DEFAULT) {//Prefix and tag
                    rec.sendMessage(ChatUtils.format(nsPlayer.getPrefix() + nsPlayer.getRank().getNameColor() + " " + p.getName() + " " + nsPlayer.getCurrentTag().getFormatted() + ": " + nsPlayer.getColor().getColor() + e.getMessage()));
                    continue;
                }
                //Just prefix
                rec.sendMessage(ChatUtils.format(nsPlayer.getPrefix() + nsPlayer.getRank().getNameColor() + " " + p.getName() +  ": " + nsPlayer.getColor().getColor() + e.getMessage()));
            }
            return; //Cool
        }

        e.setCancelled(true);
        for (Player rec : e.getRecipients()){ //Possibly just tag
            if (nsPlayer.getCurrentTag() != PlayerTag.DEFAULT) {
                rec.sendMessage(ChatUtils.format(nsPlayer.getCurrentTag().getFormatted() + nsPlayer.getRank().getNameColor() + " " + p.getName()  + ": " + nsPlayer.getColor().getColor() + e.getMessage()));
                continue;
            }

            rec.sendMessage(ChatUtils.format(p.getName() + ": " + nsPlayer.getColor().getColor() + e.getMessage()));
        }
        return;
    }

    @EventHandler
    public void on(PlayerMoveEvent e) {

        if (e.getFrom().getX() == e.getTo().getX() && e.getFrom().getZ() == e.getTo().getZ()) {
            return;
        }

        UHCPlayer gamePlayer = UHCPlayer.get(e.getPlayer().getUniqueId());

        if (gamePlayer.isFrozen()) {
            e.setTo(e.getFrom());
            return;
        }

        if (gamePlayer.isSpectator() || gamePlayer.isStaffMode()) return;

        if (GameState.getState() == GameState.STARTING) {
            e.setTo(e.getFrom());
            return;
        }

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
            Block b = e.getClickedBlock();
            Player p = e.getPlayer();
            if(b.getType() == Material.AIR) return;

            if (b.getType().toString().contains("FENCE")) {
                e.setCancelled(true);
            }

            BlockState state = b.getState();
            if (state instanceof org.bukkit.material.Skull) { //this test if b is a skull
                if (p.getItemInHand().getType() == Material.AIR){
                    Skull s = (Skull) b;
                    String owner = s.getOwner();
                    if(owner != null){
                        p.sendMessage(ChatUtils.message("&eThis is the head of&8: &b" + owner));
                        if(NSPlayer.get(owner) != null){
                            p.sendMessage(ChatUtils.message("&eThey are a " + NSPlayer.get(owner).getRank().getPrefix()));
                        }
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
