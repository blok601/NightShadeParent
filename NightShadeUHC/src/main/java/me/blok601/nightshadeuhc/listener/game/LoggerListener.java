package me.blok601.nightshadeuhc.listener.game;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.command.staff.PvPCommand;
import me.blok601.nightshadeuhc.entity.object.CombatLogger;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.entity.object.PlayerRespawn;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.manager.LoggerManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
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
public class LoggerListener implements Listener {

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
            if (!PvPCommand.isEnabled() && event.getEntity().hasMetadata("logger")) {
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
    public void onDeath(EntityDeathEvent event) {
        if (GameState.getState() == GameState.INGAME || GameState.getState() == GameState.MEETUP) {
            if (event.getEntity() instanceof Zombie && event.getEntity().isCustomNameVisible() && event.getEntity().getCustomName() != null) {
                Zombie zombie = (Zombie) event.getEntity();

                String name = zombie.getName();

                CombatLogger logger = LoggerManager.getInstance().getLogger(name);

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

                    UHC.loggedOutPlayers.remove(logger.getUuid());

                    PlayerRespawn object = new PlayerRespawn(logger.getArmor(), logger.getInventory(), logger.getZombie().getLocation());
                    GameManager.get().getInvs().put(logger.getUuid(), object);

                    Bukkit.broadcastMessage(ChatUtils.format("&5" + name + " (Logger) &9 was killed"));
                    LoggerManager.getInstance().getDeadLoggers().add(logger.getUuid());
                    logger.remove();

                }

            }
        }

    }


}