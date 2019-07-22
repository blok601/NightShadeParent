package me.blok601.nightshadeuhc.listener.game;

import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.command.staff.PvPCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.object.CombatLogger;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.entity.object.PlayerRespawn;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.manager.LoggerManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 9/23/2017.
 */
public class LoggerListener implements Listener {

    @EventHandler
    public void onCombust(EntityCombustEvent event) {

        if (event.getEntity() instanceof ArmorStand) {

            ArmorStand armorStand = (ArmorStand) event.getEntity();

            if (!PvPCommand.isEnabled() && armorStand.hasMetadata("logger")) {
                event.setCancelled(true);
            }

        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof ArmorStand) {
            if (!PvPCommand.isEnabled() && event.getEntity().hasMetadata("logger")) {
                event.setCancelled(true);
            }
        }

        if(event.getEntity() instanceof Player){
            if(event.getDamager() instanceof ArmorStand){
                if(event.getDamager().hasMetadata("logger")){
                    event.setCancelled(true); //Don't allow them to hit players
                }
            }
        }


    }


    @EventHandler
    public void onDeath(EntityDamageByEntityEvent event) {
        if (GameState.gameHasStarted()) {
            if (event.getEntity() instanceof ArmorStand) {
                ArmorStand armorStand = (ArmorStand) event.getEntity();

                if (event.getDamager() instanceof Player || event.getDamager() instanceof Arrow) {
                    CombatLogger logger = LoggerManager.getInstance().getLogger(armorStand.getEntityId());
                    if (logger != null) {
                        //event.getDrops().clear();

                        Player killer;
                        if (event.getDamager() instanceof Player) {
                            killer = (Player) event.getDamager();
                        } else {
                            Arrow arrow = (Arrow) event.getDamager();
                            if (!(arrow.getShooter() instanceof Player)) {
                                return;
                            }

                            killer = (Player) arrow.getShooter();
                        }

                        for (ItemStack itemStack : logger.getInventory()) {
                            if (itemStack == null || itemStack.getType() == Material.AIR) continue;
                            event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), itemStack);
                        }

                        for (ItemStack itemStack : logger.getArmor()) {
                            if (itemStack == null || itemStack.getType() == Material.AIR) continue;
                            event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), itemStack);
                        }

                        ((ExperienceOrb) event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(),
                                EntityType.EXPERIENCE_ORB)).setExperience((int) logger.getExp()); // Might be buggy, test

                        UHC.loggedOutPlayers.remove(logger.getUuid());

                        PlayerRespawn object = new PlayerRespawn(logger.getArmor(), logger.getInventory(), logger.getArmorStand().getLocation());
                        GameManager.get().getInvs().put(logger.getUuid(), object);

                        //Bukkit.broadcastMessage(ChatUtils.format("&5" + name + " (Logger) &9 was killed"));
                        ChatUtils.sendAll("&7" + logger.getPlayerName() + " &7(Logger) &4was killed.");
                        logger.getArmorStand().getWorld().strikeLightningEffect(logger.getArmorStand().getLocation().add(0, 10, 0));
                        ItemStack skull1 = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                        ItemStack newSkull1 = new ItemBuilder(skull1).skullOwner(logger.getPlayerName()).name(logger.getPlayerName()).make();
                        logger.getArmorStand().getWorld().dropItemNaturally(logger.getArmorStand().getLocation(), newSkull1);
                        LoggerManager.getInstance().getDeadLoggers().add(logger.getUuid());
                        logger.remove();

                        //Do stuff for killer
                        UHCPlayer uhcPlayer = UHCPlayer.get(killer);
                        uhcPlayer.addKill(1);
                        uhcPlayer.addPoints(1);

                        if (GameManager.get().getKills().containsKey(killer.getUniqueId())) {
                            GameManager.get().getKills().replace(killer.getUniqueId(), GameManager.get().getKills().get(killer.getUniqueId()) + 1);
                        } else {
                            GameManager.get().getKills().put(killer.getUniqueId(), 1);
                        }



                    } else {
                        Core.get().getLogManager().log(Logger.LogType.DEBUG, "Logger was null here!");
                    }
                }



            }
        }
    }


}