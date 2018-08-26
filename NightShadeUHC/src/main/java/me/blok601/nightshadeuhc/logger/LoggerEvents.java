package me.blok601.nightshadeuhc.logger;

import me.blok601.nightshadeuhc.GameState;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.commands.extras.PvP;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 9/23/2017.
 */
public class LoggerEvents implements Listener {

    @EventHandler
    public void onCombust(EntityCombustEvent event) {

        if (event.getEntity() instanceof Zombie) {

            Zombie zombie = (Zombie) event.getEntity();

            if (zombie.isCustomNameVisible() && zombie.getCustomName() != null) {
                event.setCancelled(true);
            }

        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof Zombie) {
            if (!PvP.isEnabled() && event.getEntity().hasMetadata("logger")) {
                event.setCancelled(true);
            }
        }

        if(event.getEntity() instanceof Player){
            if(event.getDamager() instanceof Zombie){
                if(event.getDamager().hasMetadata("logger")){
                    event.setCancelled(true); //Don't allow them to hit players
                }
            }
        }


    }


    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (GameState.getState() == GameState.INGAME || GameState.getState() == GameState.MEETUP) {
            if (event.getEntity() instanceof Zombie && event.getEntity().isCustomNameVisible() && event.getEntity().getCustomName() != null) {
                Zombie zombie = (Zombie) event.getEntity();

                String name = zombie.getName();

                CombatLogger logger = LoggerHandler.getInstance().getLogger(name);

                if (logger != null) {
                    event.getDrops().clear();

                    for (ItemStack itemStack : logger.getInventory()) {
                        if(itemStack == null || itemStack.getType() == Material.AIR) continue;
                        event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), itemStack);
                    }

                    for (ItemStack itemStack : logger.getArmor()) {
                        if(itemStack == null || itemStack.getType() == Material.AIR) continue;
                        event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), itemStack);
                    }

                    ((ExperienceOrb) event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(),
                            EntityType.EXPERIENCE_ORB)).setExperience((int) logger.getExp()); // Might be buggy, test

                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
                    if(offlinePlayer.hasPlayedBefore()) {
                        UHC.players.remove(offlinePlayer.getUniqueId());
                    }

                    Bukkit.broadcastMessage(ChatUtils.format("&5" + name + " (Logger) &9 was killed"));
                    LoggerHandler.getInstance().getDeadLoggers().add(logger.getUuid());
                    logger.remove();

                }

            }
        }

    }


}