package me.blok601.nightshadeuhc.listeners.game;

import me.blok601.nightshadeuhc.GameState;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.MConf;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
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
            if(p.getKiller() != null){
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
            uhcPlayer.setInArena(false);
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);
            uhcPlayer.leaveArena();
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
