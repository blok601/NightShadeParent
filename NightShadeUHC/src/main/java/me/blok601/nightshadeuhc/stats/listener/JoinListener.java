package me.blok601.nightshadeuhc.stats.listener;

import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.LocationUtils;
import me.blok601.nightshadeuhc.GameState;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.commands.extras.Freeze;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.object.CachedColor;
import me.blok601.nightshadeuhc.entity.object.PlayerRespawnObject;
import me.blok601.nightshadeuhc.logger.CombatLogger;
import me.blok601.nightshadeuhc.logger.LoggerHandler;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.manager.Settings;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.scoreboard.ScoreboardManager;
import me.blok601.nightshadeuhc.server.PlayerTracker;
import me.blok601.nightshadeuhc.teams.TeamManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import us.myles.ViaVersion.api.ViaVersion;

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
        if(ViaVersion.getInstance().getPlayerVersion(player) == 47){
            //Deprecated but we don't care cuz we savages!
            gamePlayer.setUsingOldVersion(false);
        }else{
            gamePlayer.setUsingOldVersion(true);
        }
        player.sendMessage(ChatUtils.message("&5Welcome &5back to the NightShadePvP Network!"));
        ScoreboardManager scoreboardManager = UHC.get().getScoreboardManager();
       scoreboardManager.addToPlayerCache(player);

        Scoreboard scoreboard = scoreboardManager.getPlayerScoreboard(player).getBukkitScoreboard();
        for (CachedColor cachedColor : GameManager.getColors()){
            if(scoreboard.getTeam(cachedColor.getId()) != null) continue;
            Team team = scoreboard.registerNewTeam(cachedColor.getId());
            team.setPrefix(cachedColor.getColor());
            team.addEntry(cachedColor.getPlayer());
        }

        if(Freeze.getToFreeze().contains(player.getUniqueId())){
            gamePlayer.setFrozen(true);
            Freeze.getToFreeze().remove(player.getUniqueId());
        }

        PlayerTracker tracker = new PlayerTracker(player);
        tracker.setupCountry();
        new BukkitRunnable() {
            @Override
            public void run() {
                int amt;
                amt = PlayerUtils.locations.getOrDefault(tracker.getCountry(), 0);

                PlayerUtils.locations.put(tracker.getCountry(), amt + 1);
            }
        }.runTaskLater(UHC.get(), 5);

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
            CombatLogger logger = LoggerHandler.getInstance().getLogger(e.getPlayer().getName());
            if (logger != null) {
                logger.remove();
            }
        }

        if (Settings.getInstance().getPlayers().contains(player.getUniqueId().toString())) {
            Location location = LocationUtils.locationFromString(Settings.getInstance().getPlayers().getString(player.getUniqueId().toString()));
            player.teleport(location);
            Settings.getInstance().getPlayers().set(player.getUniqueId().toString(), null);
            Settings.getInstance().savePlayers();
        }

        if (GameManager.getRespawnQueue().contains(player.getName().toLowerCase())) {
            // They should be respawned
            //TODO: Stopped here
            GameManager.getRespawnQueue().remove(player.getName().toLowerCase());
            PlayerRespawnObject obj = GameManager.getInvs().get(player.getUniqueId());
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
                player.getInventory().clear();
                player.getInventory().setArmorContents(null);
                player.chat("/rea");
            }

            if (targetUHCPlayer.isVanished()) targetUHCPlayer.unVanish();

            player.setGameMode(GameMode.SURVIVAL);

            GameManager.getInvs().remove(player.getUniqueId());
            player.sendMessage(ChatUtils.message(ChatUtils.message("&aYou have been respawned!")));
        }


        if (GameState.gameHasStarted()) {
            StringBuilder builder = new StringBuilder();
            ScenarioManager.getEnabledScenarios().forEach(scenario -> builder.append(scenario.getName()).append(", "));
            String scenarios = builder.toString().trim();
            player.sendMessage(ChatUtils.format("&5&m-----------------------------------"));
            player.sendMessage(ChatUtils.format("&e&lHost: &3" + GameManager.getHost().getName()));
            if (scenarios.length() > 0) {
                player.sendMessage(ChatUtils.format("&e&lScenarios: &3" + scenarios.substring(0, builder.length() - 1)));
            } else {
                player.sendMessage(ChatUtils.format("&e&lScenarios: &3None"));
            }
            player.sendMessage(ChatUtils.format("&e&lTeamSize: &3" + (GameManager.isIsTeam() ? TeamManager.getInstance().getTeamSize() : "FFA")));

            player.sendMessage(" ");
            player.sendMessage(ChatUtils.format("&e&lFinal Heal Time: &3" + GameManager.getFinalHealTime() / 60 + " minutes"));
            player.sendMessage(ChatUtils.format("&e&lPvP Time: &3" + GameManager.getPvpTime() / 60 + " minutes"));
            player.sendMessage(ChatUtils.format("&e&lMeetup Time: &3" + GameManager.getBorderTime() / 60 + " minutes"));
            player.sendMessage(" ");
            player.sendMessage(ChatUtils.format("&e&lMatchpost: &3" + Core.get().getMatchpost()));

            player.sendMessage(ChatUtils.format("&5&m-----------------------------------"));
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Settings.getInstance().getPlayers().set(e.getPlayer().getUniqueId().toString(), LocationUtils.locationToString(e.getPlayer()));
        Settings.getInstance().savePlayers();
        UHC.get().getScoreboardManager().removeFromPlayerCache(e.getPlayer());

        new BukkitRunnable() {
            @Override
            public void run() {
                if (Settings.getInstance().getPlayers().contains(e.getPlayer().getUniqueId().toString())) {
                    Location location = LocationUtils.locationFromString(Settings.getInstance().getPlayers().getString(e.getPlayer().getUniqueId().toString()));
                    e.getPlayer().teleport(location);
                    Settings.getInstance().getPlayers().set(e.getPlayer().getUniqueId().toString(), null);
                    Settings.getInstance().savePlayers();
                }
            }
        }.runTaskLater(UHC.get(), (20 * 5 * 60) + 1);

        Player p = e.getPlayer();
        UHCPlayer gamePlayer = UHCPlayer.get(p.getUniqueId());

        if (gamePlayer.isSpectator() || gamePlayer.isStaffMode()) {
            return;
        }

        if (gamePlayer.isFrozen()) {
            Freeze.getToFreeze().add(p.getUniqueId());
        }

        if (UHC.players.contains(p.getUniqueId())) {
            if (GameState.getState() == GameState.INGAME || GameState.getState() == GameState.MEETUP) {
                CombatLogger logger = LoggerHandler.getInstance().getLogger(e.getPlayer().getName());
                if (logger == null) { // create a logger, cause its null :D
                    new CombatLogger(e.getPlayer());
                }
            }
        }
    }


    @EventHandler
    public void onKick(PlayerKickEvent e) {
        Settings.getInstance().getPlayers().set(e.getPlayer().getUniqueId().toString(), LocationUtils.locationToString(e.getPlayer()));
        Settings.getInstance().savePlayers();
        UHC.get().getScoreboardManager().removeFromPlayerCache(e.getPlayer());

        new BukkitRunnable() {
            @Override
            public void run() {
                if (Settings.getInstance().getPlayers().contains(e.getPlayer().getUniqueId().toString())) {
                    Location location = LocationUtils.locationFromString(Settings.getInstance().getPlayers().getString(e.getPlayer().getUniqueId().toString()));
                    e.getPlayer().teleport(location);
                    Settings.getInstance().getPlayers().set(e.getPlayer().getUniqueId().toString(), null);
                    Settings.getInstance().savePlayers();
                }
            }
        }.runTaskLater(UHC.get(), 20 * 5 * 60);

        Player p = e.getPlayer();
        UHCPlayer gamePlayer = UHCPlayer.get(p.getUniqueId());

        if (gamePlayer.isSpectator() || gamePlayer.isStaffMode()) {
            return;
        }

        if (gamePlayer.isFrozen()) {
            Freeze.getToFreeze().add(p.getUniqueId());
        }

        if (UHC.players.contains(p.getUniqueId())) {
            if (GameState.getState() == GameState.INGAME || GameState.getState() == GameState.MEETUP) {
                CombatLogger logger = LoggerHandler.getInstance().getLogger(e.getPlayer().getName());
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
        if (GameManager.isWhitelistEnabled()) {

            System.out.println(user.getRank().toString());
            if (user.getRank().getValue() >= Rank.TRIAL.getValue()) {
                e.allow();
                return;
            }

            if (GameManager.getRespawnQueue().contains(e.getPlayer().getName().toLowerCase())) {
                e.allow();
            }

            if (!GameManager.getWhitelist().contains(e.getPlayer().getName().toLowerCase()) && !LoggerHandler.getInstance().getDeadLoggers().contains(p.getUniqueId())) {
                if (GameManager.getRespawnQueue().contains(e.getPlayer().getName().toLowerCase())) {
                    e.allow();
                    return;
                }
                e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "You are not on the whitelist!");
            } else {
                e.allow();
            }

            if (GameManager.getDeathBans().contains(p.getUniqueId())) {
                e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "You have already died!");
                return;
            }

            if (LoggerHandler.getInstance().getDeadLoggers().contains(p.getUniqueId())) {
                e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "You have died! Follow us on twitter @NightShadePvPMC for more!");
            }

        }

        if (Bukkit.getOnlinePlayers().size() == GameManager.getMaxPlayers()) {
            if (!user.hasRank(Rank.YOUTUBE)) {
                e.disallow(PlayerLoginEvent.Result.KICK_FULL, "The server is full!");
            } else {
                e.allow();
            }
        }
    }


}
