package me.blok601.nightshadeuhc.listeners.game;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import me.blok601.nightshadeuhc.GameState;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.MConf;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.object.PlayerRespawnObject;
import me.blok601.nightshadeuhc.events.CustomDeathEvent;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Blok on 7/15/2018.
 */
public class GameDeathListener implements Listener {

    @EventHandler
    public void onPvPDeath(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;

        if (!GameState.gameHasStarted()) return;

        Player p = (Player) e.getEntity();
        UHCPlayer uhcPlayer = UHCPlayer.get(p);
        if (uhcPlayer.isInArena()) return;
        NSPlayer user = NSPlayer.get(p);
        Player damager = null;
        boolean shot = false;

        UHCPlayer gamePlayer1 = null;

        if (e.getDamager() instanceof Player) { //Sort out if player was shot or not
            damager = (Player) e.getDamager();
            gamePlayer1 = UHCPlayer.get(damager);
        } else if (e.getDamager() instanceof Arrow) {
            Arrow a = (Arrow) e.getDamager();
            if (a.getShooter() instanceof Player) {
                damager = (Player) a.getShooter();
                gamePlayer1 = UHCPlayer.get(damager);
                shot = true;
            } else {
                damager = null;
                shot = false;
            }
        } else {
            damager = null;
        }

        if (e.getFinalDamage() >= p.getHealth()) { //Check if player would have died on the hit
            ArrayList<ItemStack> items = new ArrayList<>(); //Get their loot
            for (ItemStack i : p.getInventory().getContents()) {
                if (i == null || i.getType() == Material.AIR) continue;
                items.add(i);
            }

            for (ItemStack i : p.getInventory().getArmorContents()) {
                if (i == null || i.getType() == Material.AIR) continue;
                items.add(i);
            }

            e.setDamage(0);
            e.setCancelled(true);
            p.getWorld().strikeLightningEffect(p.getLocation());
            CustomDeathEvent customDeathEvent = new CustomDeathEvent(p, damager, items, p.getLocation(), true);
            Bukkit.getPluginManager().callEvent(customDeathEvent);

            if (!customDeathEvent.isCancelled()) {
                String deathMessage = ""; //Figure out death message based on how they died
                if (damager != null) {
                    if (shot) {
                        deathMessage = "&5" + (uhcPlayer.isDisguised() ? uhcPlayer.getDisguisedName() : uhcPlayer.getName()) + " &9was shot by &5" + (gamePlayer1.isDisguised() ? gamePlayer1.getDisguisedName() : gamePlayer1.getName()) + "(" + Math.floor(p.getLocation().distance(damager.getLocation())) + "m)";
                    } else {
                        if (gamePlayer1.isDisguised() && gamePlayer1.getDisguisedName() != null) {
                            deathMessage = "&5" + (uhcPlayer.isDisguised() ? uhcPlayer.getDisguisedName() : uhcPlayer.getName()) + " &9was killed by &5" + (gamePlayer1.isDisguised() ? gamePlayer1.getDisguisedName() : gamePlayer1.getName()) + "";
                        } else {
                            deathMessage = "&5" + (uhcPlayer.isDisguised() ? uhcPlayer.getDisguisedName() : uhcPlayer.getName()) + " &9was killed by &5" + (gamePlayer1.isDisguised() ? gamePlayer1.getDisguisedName() : gamePlayer1.getName()) + "";
                        }
                    }
                } else {
                    deathMessage = "&5" + (uhcPlayer.isDisguised() ? uhcPlayer.getDisguisedName() : uhcPlayer.getName()) + " &9died.";
                }

                PlayerRespawnObject inv = new PlayerRespawnObject();
                inv.setArmor(p.getInventory().getArmorContents());
                inv.setItems(p.getInventory().getContents());
                inv.setLocation(p.getLocation());
                GameManager.getInvs().put(p.getUniqueId(), inv);

                p.setHealth(p.getMaxHealth());
                p.setFoodLevel(20);
                p.getInventory().clear();
                p.getInventory().setArmorContents(null);
                p.setExp(0);
                if (customDeathEvent.isDropItems()) {
                    ItemStack skull1 = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                    ItemStack newSkull1 = new ItemBuilder(skull1).skullOwner(p.getName()).name(p.getName()).make();

                    p.getLocation().getWorld().dropItemNaturally(p.getLocation(), newSkull1);
                    items.forEach(itemStack -> p.getWorld().dropItemNaturally(p.getLocation(), itemStack));
                }
                if (damager != null) damager.setExp(damager.getExp() + p.getExp());
                Bukkit.broadcastMessage(ChatUtils.format(deathMessage));
            }

            //Calculate points and changes
            UHC.players.remove(p.getUniqueId());

            if (damager != null) {
                gamePlayer1.addKill(1);
                gamePlayer1.addPoints(1);

                if (GameManager.getKills().containsKey(damager.getUniqueId())) {
                    GameManager.getKills().replace(damager.getUniqueId(), GameManager.getKills().get(damager.getUniqueId()) + 1);
                } else {
                    GameManager.getKills().put(damager.getUniqueId(), 1);
                }

//                if (gamePlayer1.getKillTimer() != 0) {
//                    //They got a double, triple, etc
//                    gamePlayer1.setKillStreak(gamePlayer1.getKillStreak() + 1);
//                    if (gamePlayer1.getKillStreak() == 2) {
//                        Bukkit.broadcastMessage(ChatUtils.message("&6" + gamePlayer1.getName() + " &egot a &bdouble kill!"));
//                        damager.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, 2));
//                    } else if (gamePlayer1.getKillStreak() == 3) {
//                        Bukkit.broadcastMessage(ChatUtils.message("&6" + gamePlayer1.getName() + " &egot a &btriple kill!"));
//                        damager.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, 3));
//                    } else if (gamePlayer1.getKillStreak() == 4) {
//                        Bukkit.broadcastMessage(ChatUtils.message("&6" + gamePlayer1.getName() + " &egot a &cquadruple kill!"));
//                        damager.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, 4));
//                    } else {
//                        Bukkit.broadcastMessage(ChatUtils.message("&6" + gamePlayer1.getName() + " &ehas a &b" + gamePlayer1.getKillStreak() + " killstreak!"));
//                    }
//
//                }
//                gamePlayer1.startKillTimerTask();
            }

            uhcPlayer.setDeaths(uhcPlayer.getDeaths() + 1);
            uhcPlayer.setGamesPlayed(uhcPlayer.getGamesPlayed() + 1);
            double points = -0.25;
            if (GameManager.getKills().containsKey(p.getUniqueId())) {
                points += GameManager.getKills().get(p.getUniqueId());
            }
            points += uhcPlayer.getChangedLevel();
            uhcPlayer.addPoints(points);
            uhcPlayer.changed();

            p.sendMessage(ChatUtils.message("&eYou have died! Thank you for playing on NightShadePvP!"));
            p.sendMessage(ChatUtils.message("&eJoin the Discord at discord.me/NightShadePvP for updates and more!"));
//            p.sendMessage(ChatUtils.format("&5&e----------------------------"));
//            p.sendMessage(ChatUtils.message("&bYour rewards:"));
//            p.sendMessage(ChatUtils.format(points >= 0 ? "&a&o+" + points  + " points" : "&c&o-" + points + " points"));
//            p.sendMessage(ChatUtils.format());

            if (user.hasRank(Rank.DRAGON)) { //Dragon and above can spectate the games, otherwise kick
                p.setAllowFlight(true);
                p.setFlying(true);
                p.setFlySpeed(0.2F);
                uhcPlayer.spec();
            } else {
                p.spigot().respawn();
                p.teleport(MConf.get().getSpawnLocation().asBukkitLocation(true));
                GameManager.getWhitelist().remove(p.getName().toLowerCase());
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (p.isOnline()) {
                            p.kickPlayer("You have died! Follow us on twitter @NightShadePvPMC for more!");
                        }
                    }
                }.runTaskLater(UHC.get(), 20 * 60);
            }
        } else {
            if (shot) {
                double damage = e.getFinalDamage();
                double health = (p.getHealth() - damage);
                DecimalFormat format = new DecimalFormat("##.#");
                format.setRoundingMode(RoundingMode.HALF_UP);
                String f = format.format(health);
                damager.sendMessage(me.blok601.nightshadeuhc.utils.ChatUtils.message("&9" + p.getName() + " &b now has &9" + f + "❤"));
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        if (!UHC.players.contains(event.getPlayer().getUniqueId())) {
            event.getPlayer().teleport(MConf.get().getSpawnLocation().asBukkitLocation(true));
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

        if (!GameState.gameHasStarted()) return;

        Player p = e.getEntity();
        UHCPlayer uhcPlayer = UHCPlayer.get(p);
        if (uhcPlayer.isInArena()) return;
        NSPlayer user = NSPlayer.get(p);
        Player damager = null;
        UHCPlayer gamePlayer1 = null;
        if (p.getKiller() != null){
            damager = p.getKiller();
            gamePlayer1 = UHCPlayer.get(damager);
        }

        e.getDrops().clear();

        ArrayList<ItemStack> items = new ArrayList<>(); //Get their loot
        for (ItemStack i : p.getInventory().getContents()) {
            if (i == null || i.getType() == Material.AIR) continue;
            items.add(i);
        }

        for (ItemStack i : p.getInventory().getArmorContents()) {
            if (i == null || i.getType() == Material.AIR) continue;
            items.add(i);
        }

        p.getWorld().strikeLightningEffect(p.getLocation());
        CustomDeathEvent customDeathEvent = new CustomDeathEvent(p, damager, items, p.getLocation(), true);
        Bukkit.getPluginManager().callEvent(customDeathEvent);

        if (!customDeathEvent.isCancelled()) {
            String deathMessage = ""; //Figure out death message based on how they died
            if(damager == null){
                deathMessage = "&5" + p.getName() + " &9died.";
            }else{
                deathMessage = "&5" + p.getName() + " &9was killed by &5" + damager.getName() + "";
            }

            PlayerRespawnObject inv = new PlayerRespawnObject();
            inv.setArmor(p.getInventory().getArmorContents());
            inv.setItems(p.getInventory().getContents());
            inv.setLocation(p.getLocation());
            GameManager.getInvs().put(p.getUniqueId(), inv);

            p.setHealth(p.getMaxHealth());
            p.setFoodLevel(20);
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);
            p.setExp(0);
            if (customDeathEvent.isDropItems()) {
                ItemStack skull1 = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                ItemStack newSkull1 = new ItemBuilder(skull1).skullOwner(p.getName()).name(p.getName()).make();

                p.getLocation().getWorld().dropItemNaturally(p.getLocation(), newSkull1);
                items.forEach(itemStack -> p.getWorld().dropItemNaturally(p.getLocation(), itemStack));
            }
            if (damager != null) damager.setExp(damager.getExp() + p.getExp());
            e.setDeathMessage(ChatUtils.format(deathMessage));
        }

        //Calculate points and changes
        UHC.players.remove(p.getUniqueId());

        if (damager != null) {
            gamePlayer1.addKill(1);
            gamePlayer1.addPoints(1);

            if (GameManager.getKills().containsKey(damager.getUniqueId())) {
                GameManager.getKills().replace(damager.getUniqueId(), GameManager.getKills().get(damager.getUniqueId()) + 1);
            } else {
                GameManager.getKills().put(damager.getUniqueId(), 1);
            }
        }

        uhcPlayer.setDeaths(uhcPlayer.getDeaths() + 1);
        uhcPlayer.setGamesPlayed(uhcPlayer.getGamesPlayed() + 1);
        double points = -0.25;
        if (GameManager.getKills().containsKey(p.getUniqueId())) {
            points += GameManager.getKills().get(p.getUniqueId());
        }
        points += uhcPlayer.getChangedLevel();
        uhcPlayer.addPoints(points);
        uhcPlayer.changed();

        p.sendMessage(ChatUtils.message("&eYou have died! Thank you for playing on NightShadePvP!"));
        p.sendMessage(ChatUtils.message("&eJoin the Discord at discord.me/NightShadePvP for updates and more!"));
//        p.sendMessage(ChatUtils.format("&5&e----------------------------"));
//        p.sendMessage(ChatUtils.message("&bYour rewards:"));
//            p.sendMessage(ChatUtils.format(points >= 0 ? "&a&o+" + points  + " points" : "&c&o-" + points + " points"));
//            p.sendMessage(ChatUtils.format());

        if (user.hasRank(Rank.YOUTUBE)) { //YouTube and above can spectate the games, otherwise kick
            p.setAllowFlight(true);
            p.setFlying(true);
            p.setFlySpeed(0.2F);
            uhcPlayer.spec();
        } else {
            p.spigot().respawn();
            p.teleport(MConf.get().getSpawnLocation().asBukkitLocation(true));
            GameManager.getWhitelist().remove(p.getName().toLowerCase());
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (p.isOnline()) {
                        p.kickPlayer("You have died! Follow us on twitter @NightShadePvPMC for more!");
                    }
                }
            }.runTaskLater(UHC.get(), 20 * 60);
        }
    }
}
