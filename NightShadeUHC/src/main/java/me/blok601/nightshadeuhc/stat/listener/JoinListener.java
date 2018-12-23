package me.blok601.nightshadeuhc.stat.listener;

import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.LocationUtils;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.object.CachedColor;
import me.blok601.nightshadeuhc.entity.object.CombatLogger;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.entity.object.PlayerRespawnObject;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.manager.LoggerManager;
import me.blok601.nightshadeuhc.manager.SettingsManager;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.scenario.Scenario;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.scenario.interfaces.StarterItems;
import me.blok601.nightshadeuhc.scoreboard.ScoreboardManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.PlayerUtils;
import me.blok601.nightshadeuhc.util.ScatterUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * Created by Blok on 9/4/2017.
 */
public class JoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UHCPlayer gamePlayer = UHCPlayer.get(player);

        gamePlayer.setSpectator(false);
        gamePlayer.setReceiveHelpop(true);
        gamePlayer.setKillStreak(0);
        player.sendMessage(ChatUtils.message("&5Welcome &5back to the NightShadePvP Network!"));
        ScoreboardManager scoreboardManager = UHC.get().getScoreboardManager();
       scoreboardManager.addToPlayerCache(player);

        Scoreboard scoreboard = scoreboardManager.getPlayerScoreboard(player).getBukkitScoreboard();
        for (CachedColor cachedColor : GameManager.get().getColors()){
            if(scoreboard.getTeam(cachedColor.getId()) != null) continue;
            Team team = scoreboard.registerNewTeam(cachedColor.getId());
            team.setPrefix(cachedColor.getColor());
            team.addEntry(cachedColor.getPlayer());
        }

        if (!GameState.gameHasStarted()) {
            player.getEnderChest().clear();
        }

        UHCPlayer gameP;
        for (Player pl : Bukkit.getOnlinePlayers()) {
            gameP = UHCPlayer.get(pl.getUniqueId());
            if (gameP != null) {
                if (gameP.isSpectator())
                    player.hidePlayer(pl);
            }

        }

        UHCPlayer gp;
        for (Player pls : Bukkit.getOnlinePlayers()) {
            gp = UHCPlayer.get(pls.getUniqueId());
            if (gp.isSpectator()) {
                player.hidePlayer(pls);
            }
        }

        if (gamePlayer.isSpectator()) return;
        if (GameState.getState() == GameState.INGAME || GameState.getState() == GameState.MEETUP) {
            CombatLogger logger = LoggerManager.getInstance().getLogger(e.getPlayer().getName());
            if (logger != null) {
                logger.remove();
            }
        }

        if (SettingsManager.getInstance().getPlayers().contains(player.getUniqueId().toString())) {
            Location location = LocationUtils.locationFromString(SettingsManager.getInstance().getPlayers().getString(player.getUniqueId().toString()));
            player.teleport(location);
            SettingsManager.getInstance().getPlayers().set(player.getUniqueId().toString(), null);
            SettingsManager.getInstance().savePlayers();
        }

        if (GameManager.get().getRespawnQueue().contains(player.getName().toLowerCase())) {
            // They should be respawned
            //TODO: Stopped here
            GameManager.get().getRespawnQueue().remove(player.getName().toLowerCase());
            PlayerRespawnObject obj = GameManager.get().getInvs().get(player.getUniqueId());
            UHCPlayer targetUHCPlayer = UHCPlayer.get(player);
            player.teleport(obj.getLocation());
            player.getInventory().setArmorContents(obj.getArmor());
            player.getInventory().setContents(obj.getItems());
            if (targetUHCPlayer.isSpectator()) {
                targetUHCPlayer.unspec();
            }

            if (targetUHCPlayer.isStaffMode()) {
                Bukkit.getOnlinePlayers().forEach(o -> o.showPlayer(player));
                player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
                targetUHCPlayer.setStaffMode(false);
                targetUHCPlayer.getPlayer().getInventory().clear();
                targetUHCPlayer.getPlayer().getInventory().setArmorContents(null);
                targetUHCPlayer.getPlayer().chat("/rea");
            }

            if (targetUHCPlayer.isVanished()) targetUHCPlayer.unVanish();

            player.setGameMode(GameMode.SURVIVAL);
            UHC.players.add(player.getUniqueId());
            GameManager.get().getInvs().remove(player.getUniqueId());
            player.sendMessage(ChatUtils.message("&aYou have been respawned!"));
        }

        if (GameManager.get().getLateScatter().contains(player.getName().toLowerCase())) {
            //They should be late started
            ScatterUtil.scatterPlayer(GameManager.get().getWorld(), (int) GameManager.get().getBorderSize(), player);
            UHC.players.add(player.getUniqueId());
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.setLevel(0);
            player.setExp(0F);
            player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 10));
            for (Scenario scenario : ScenarioManager.getEnabledScenarios()) {
                if (scenario instanceof StarterItems) {

                    StarterItems starterItems = (StarterItems) scenario;

                    //UHCPlayerColl.get().getAllPlaying().forEach(uhcPlayer -> PlayerUtils.giveBulkItems(uhcPlayer.getPlayer(), starterItems.getStarterItems
                    for (ItemStack stack : starterItems.getStarterItems()) {
                        PlayerUtils.giveItem(stack, player);
                    }
                }
            }
            ScatterUtil.scatterPlayer(GameManager.get().getWorld(), (int) GameManager.get().getBorderSize(), player);
            player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 5, 5);
            player.sendMessage(ChatUtils.message("&eYou were scattered!"));
            GameManager.get().getLateScatter().remove(player.getName().toLowerCase());
        }


        if (GameState.gameHasStarted()) {
            StringBuilder builder = new StringBuilder();
            ScenarioManager.getEnabledScenarios().forEach(scenario -> builder.append(scenario.getName()).append(", "));
            String scenarios = builder.toString().trim();
            player.sendMessage(ChatUtils.format("&5&m-----------------------------------"));
            player.sendMessage(ChatUtils.format("&e&lHost: &3" + GameManager.get().getHost().getName()));
            if (scenarios.length() > 0) {
                player.sendMessage(ChatUtils.format("&e&lScenarios: &3" + scenarios.substring(0, builder.length() - 1)));
            } else {
                player.sendMessage(ChatUtils.format("&e&lScenarios: &3None"));
            }
            player.sendMessage(ChatUtils.format("&e&lTeamSize: &3" + (GameManager.get().isIsTeam() ? TeamManager.getInstance().getTeamSize() : "FFA")));

            player.sendMessage(" ");
            player.sendMessage(ChatUtils.format("&e&lFinal Heal Time: &3" + GameManager.get().getFinalHealTime() / 60 + " minutes"));
            player.sendMessage(ChatUtils.format("&e&lPvP Time: &3" + GameManager.get().getPvpTime() / 60 + " minutes"));
            player.sendMessage(ChatUtils.format("&e&lMeetup Time: &3" + GameManager.get().getBorderTime() / 60 + " minutes"));
            player.sendMessage(" ");
            player.sendMessage(ChatUtils.format("&e&lMatchpost: &3" + Core.get().getMatchpost()));

            player.sendMessage(ChatUtils.format("&5&m-----------------------------------"));

        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onLeave(PlayerQuitEvent e) {
        UHC.get().getScoreboardManager().removeFromPlayerCache(e.getPlayer());


        Player p = e.getPlayer();
        UHCPlayer gamePlayer = UHCPlayer.get(p.getUniqueId());

        if (gamePlayer.isSpectator() || gamePlayer.isStaffMode()) {
            return;
        }


        if (UHC.players.contains(p.getUniqueId())) {
            if (GameState.getState() == GameState.INGAME || GameState.getState() == GameState.MEETUP) {
                CombatLogger logger = LoggerManager.getInstance().getLogger(e.getPlayer().getName());
                if (logger == null) { // create a logger, cause its null :D
                    new CombatLogger(e.getPlayer());
                }
            }
        }
    }


    @EventHandler
    public void onKick(PlayerKickEvent e) {
        UHC.get().getScoreboardManager().removeFromPlayerCache(e.getPlayer());

        new BukkitRunnable() {
            @Override
            public void run() {
                if (SettingsManager.getInstance().getPlayers().contains(e.getPlayer().getUniqueId().toString())) {
                    Location location = LocationUtils.locationFromString(SettingsManager.getInstance().getPlayers().getString(e.getPlayer().getUniqueId().toString()));
                    e.getPlayer().teleport(location);
                    SettingsManager.getInstance().getPlayers().set(e.getPlayer().getUniqueId().toString(), null);
                    SettingsManager.getInstance().savePlayers();
                }
            }
        }.runTaskLater(UHC.get(), 20 * 5 * 60);

        Player p = e.getPlayer();
        UHCPlayer gamePlayer = UHCPlayer.get(p.getUniqueId());

        if (gamePlayer.isSpectator() || gamePlayer.isStaffMode()) {
            return;
        }

        if (UHC.players.contains(p.getUniqueId())) {
            if (GameState.getState() == GameState.INGAME || GameState.getState() == GameState.MEETUP) {
                CombatLogger logger = LoggerManager.getInstance().getLogger(e.getPlayer().getName());
                if (logger == null) { // create a logger, cause its null :D
                    new CombatLogger(e.getPlayer());
                }
            }
        }

    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        NSPlayer user = NSPlayer.get(p);
        if (GameManager.get().isWhitelistEnabled()) {

            System.out.println(user.getRank().toString());
            if (user.getRank().getValue() >= Rank.TRIAL.getValue()) {
                e.allow();
                return;
            }

            if (GameManager.get().getRespawnQueue().contains(e.getPlayer().getName().toLowerCase())) {
                e.allow();
                LoggerManager.getInstance().getDeadLoggers().remove(p.getUniqueId());
                return;
            }

            if (!GameManager.get().getWhitelist().contains(e.getPlayer().getName().toLowerCase()) && !LoggerManager.getInstance().getDeadLoggers().contains(p.getUniqueId())) {
                if (GameManager.get().getRespawnQueue().contains(e.getPlayer().getName().toLowerCase())) {
                    e.allow();
                    return;
                }
                e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "You are not on the whitelist!");
            } else {
                e.allow();
            }

            if (GameManager.get().getDeathBans().contains(p.getUniqueId())) {
                e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "You have already died!");
                return;
            }

            if (LoggerManager.getInstance().getDeadLoggers().contains(p.getUniqueId())) {
                e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "You have died! Follow us on twitter @NightShadePvPMC for more!");
            }

        }

        if (Bukkit.getOnlinePlayers().size() == GameManager.get().getMaxPlayers()) {
            if (!user.hasRank(Rank.YOUTUBE)) {
                e.disallow(PlayerLoginEvent.Result.KICK_FULL, "The server is full!");
            } else {
                e.allow();
            }
        }
    }


}
