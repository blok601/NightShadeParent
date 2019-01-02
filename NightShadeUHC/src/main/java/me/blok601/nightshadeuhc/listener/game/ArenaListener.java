package me.blok601.nightshadeuhc.listener.game;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.MConf;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.object.ArenaSession;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Blok on 7/9/2018.
 */
public class ArenaListener implements Listener {


    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        if(GameState.gameHasStarted()){
            return;
        }

        Player p = e.getEntity();
        new BukkitRunnable(){
            @Override
            public void run() {
                p.spigot().respawn();
            }
        }.runTaskLater(UHC.get(), 2);
        UHCPlayer uhcPlayer = UHCPlayer.get(p);
        if(uhcPlayer.isInArena()){
            uhcPlayer.handleArenaDeath();
            if(p.getKiller() != null){
                UHCPlayer.get(p.getKiller()).handleArenaKill();
                p.getKiller().setHealth(p.getKiller().getMaxHealth());
                p.getKiller().sendMessage(ChatUtils.sendArenaMessage("&eYou killed &9" + p.getName()));
                ChatUtils.broadcastArenaMessage("&e" + p.getKiller().getName() + " &9killed &e" + p.getName());
            }

            e.getDrops().clear();
            e.getDrops().add(new ItemStack(Material.GOLDEN_APPLE));
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        UHCPlayer uhcPlayer = UHCPlayer.get(p);
        if(uhcPlayer.isInArena()){
            uhcPlayer.leaveArena();
        }
    }

    @EventHandler
    public void onAnimation(PlayerAnimationEvent e) {
        Player p = e.getPlayer();
        if (GameState.gameHasStarted()) return;
        if (e.getAnimationType() == PlayerAnimationType.ARM_SWING) {
            if (p.getItemInHand().getType() == Material.IRON_SWORD) {
                UHCPlayer uhcPlayer = UHCPlayer.get(p);
                if (uhcPlayer.isInArena()) {
                    uhcPlayer.getArenaSession().setSwordSwings(uhcPlayer.getArenaSession().getSwordSwings() + 1);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (GameState.gameHasStarted()) return;

        if (e.getDamage() == 0 || e.getFinalDamage() == 0) return;
        if (e.isCancelled()) return;

        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        if (!(e.getDamager() instanceof Player)) {
            if (e.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) e.getDamager();
                UHCPlayer shooter = UHCPlayer.get(arrow.getShooter());
                if (shooter.isInArena()) {
                    shooter.getArenaSession().setBowHits(shooter.getArenaSession().getBowHits() + 1);
                }
            }
        }

        Player p = (Player) e.getEntity();
        UHCPlayer uhcPlayer = UHCPlayer.get(p);
        if (uhcPlayer.isInArena()) {
            uhcPlayer.getArenaSession().setSwordHits(uhcPlayer.getArenaSession().getSwordHits() + 1);
        }
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        if (GameState.gameHasStarted()) return;
        ItemStack itemStack = e.getItem();
        if (itemStack.getType() != Material.GOLDEN_APPLE) return;
        UHCPlayer uhcPlayer = UHCPlayer.get(e.getPlayer());
        if (uhcPlayer.isInArena()) {
            uhcPlayer.getArenaSession().setGapplesEaten(uhcPlayer.getArenaSession().getGapplesEaten() + 1);
        }
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent e) {
        if (GameState.gameHasStarted()) return;
        if (!(e.getEntity() instanceof Arrow)) return;
        if (!(e.getEntity().getShooter() instanceof Player)) return;

        Arrow arrow = (Arrow) e.getEntity();
        Player p = (Player) arrow.getShooter();
        UHCPlayer uhcPlayer = UHCPlayer.get(p);
        if (uhcPlayer.isInArena()) {
            ArenaSession arenaSession = uhcPlayer.getArenaSession();
            arenaSession.setBowAttempts(arenaSession.getBowHits() + 1);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        if(GameState.gameHasStarted()){
            return;
        }
        Player p = e.getPlayer();
        new BukkitRunnable(){
            @Override
            public void run() {
                p.teleport(MConf.get().getArenaLocation().asBukkitLocation(true));
                ItemBuilder sword = new ItemBuilder(Material.IRON_SWORD).enchantment(Enchantment.DAMAGE_ALL, 2);
                ItemStack rod = new ItemStack(Material.FISHING_ROD);
                ItemBuilder helmet = new ItemBuilder(Material.IRON_HELMET).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                ItemBuilder chestplate = new ItemBuilder(Material.IRON_CHESTPLATE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                ItemBuilder leggings = new ItemBuilder(Material.IRON_LEGGINGS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                ItemBuilder boots = new ItemBuilder(Material.IRON_BOOTS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                ItemBuilder bow = new ItemBuilder(Material.BOW).enchantment(Enchantment.ARROW_DAMAGE, 2).enchantment(Enchantment.ARROW_INFINITE);
                ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE, 1);
                ItemStack steak = new ItemStack(Material.COOKED_BEEF, 64);
                ItemStack arrow = new ItemStack(Material.ARROW, 1);

                p.getInventory().addItem(sword.make());
                p.getInventory().addItem(rod);
                p.getInventory().addItem(bow.make());
                p.getInventory().addItem(gapple);
                p.getInventory().addItem(steak);
                p.getInventory().setItem(8, arrow);
                p.getInventory().setBoots(boots.make());
                p.getInventory().setLeggings(leggings.make());
                p.getInventory().setChestplate(chestplate.make());
                p.getInventory().setHelmet(helmet.make());
            }
        }.runTaskLater(UHC.get(), 2);
    }
}
