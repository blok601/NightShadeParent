package me.blok601.nightshadeuhc;

import com.comphenix.protocol.ProtocolLibrary;
import com.earth2me.essentials.Essentials;
import com.massivecraft.massivecore.MassivePlugin;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import com.nightshadepvp.core.entity.NSPlayer;
import com.onarandombox.MultiverseCore.MultiverseCore;
import de.robingrether.idisguise.api.DisguiseAPI;
import me.blok601.nightshadeuhc.command.Commands;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.command.player.teams.SendCoordsCommand;
import me.blok601.nightshadeuhc.command.player.teams.TeamChatCommand;
import me.blok601.nightshadeuhc.component.ComponentHandler;
import me.blok601.nightshadeuhc.component.GoldenHeadRecipe;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.listener.gui.EnchantHider;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.manager.ListenerHandler;
import me.blok601.nightshadeuhc.manager.LoggerManager;
import me.blok601.nightshadeuhc.manager.SettingsManager;
import me.blok601.nightshadeuhc.manager.packet.OldEnchanting;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.scoreboard.PlayerScoreboard;
import me.blok601.nightshadeuhc.scoreboard.ScoreboardManager;
import me.blok601.nightshadeuhc.stat.handler.StatsHandler;
import me.blok601.nightshadeuhc.task.PregenTask;
import me.blok601.nightshadeuhc.task.ScoreboardHealthTask;
import me.blok601.nightshadeuhc.task.StaffTrackTask;
import me.blok601.nightshadeuhc.task.WorldLoadTask;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.Lag;
import me.blok601.nightshadeuhc.util.Util;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class UHC extends MassivePlugin implements PluginMessageListener {

    private static UHC i;

    public UHC() {
        UHC.i = this;
    }

    public static UHC get() {
        return i;
    }

    private static Essentials ess;
    private static DisguiseAPI api;
    private static MultiverseCore multiverseCore;


    private ScenarioManager sm;
    private ScoreboardManager scoreboardManager;

    private MongoCollection<Document> gameCollection;

    public static HashSet<UUID> players = new HashSet<>();
    public static HashSet<UUID> loggedOutPlayers = new HashSet<>();

    @Override
    public void onEnableInner() {

        if (!setupMultiverse()) {
            Core.get().getLogManager().log(Logger.LogType.SEVERE, "Multiverse wasn't found! Disabling plugin!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }


        this.activateAuto();

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        if (Bukkit.getPluginManager().getPlugin("ViaRewind") == null) {
            GameManager.get().setServerType("UHC1");
        } else {

            GameManager.get().setServerType("UHC2");

            hideEnchants();
            new OldEnchanting(this);

        }


        getConfig().options().copyDefaults(true);
        saveConfig();

        Commands.setup();
        GameState.setState(GameState.WAITING);
        SettingsManager.getInstance().setup(this);

        ComponentHandler.getInstance().setup();
        StatsHandler.getInstance().setup();

        scoreboardManager = new ScoreboardManager();

        GameManager.get().setup();

        sm = new ScenarioManager();
        api = getServer().getServicesManager().getRegistration(DisguiseAPI.class).getProvider();
        ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");

        new GoldenHeadRecipe();

        registerCommands();
        registerListeners();
        setupExtraDatabase();
        setupTasks();

        Bukkit.getConsoleSender().sendMessage(ChatUtils.message("&aNightShadePvPUHC " + getDescription().getVersion() + " has been successfully enabled!"));
        Bukkit.getConsoleSender().sendMessage(ChatUtils.message("&eDetected Server&8: &3" + GameManager.get().getServerType()));

    }



    public void onDisable() {
        //LoggerHandler.getInstance().getLoggers().forEach(combatLogger -> LoggerHandler.getInstance().removeLogger(combatLogger));
        LoggerManager.getInstance().getLoggers().clear();
        Bukkit.getMessenger().unregisterIncomingPluginChannel(this, "BungeeCord", this);
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");

    }

    private void setupTasks() {

        new ScoreboardHealthTask(scoreboardManager).runTaskTimerAsynchronously(this, 0, 60);
        new StaffTrackTask().runTaskTimer(this, 0, 100);
        new PregenTask().runTaskTimer(this, 0, 40);

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            scoreboardManager.getPlayerScoreboards().values().forEach(PlayerScoreboard::update);
        }, 0L, 35L);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (Lag.getTPS() > 18) {
                    Util.staffLog("&aThe TPS is currently: " + Lag.getTPS() + " &6Status: &aGREAT");
                } else if (Lag.getTPS() < 18 && Lag.getTPS() > 16) {
                    Util.staffLog("&eThe TPS is currently: " + Lag.getTPS() + " &6Status: &eUnstable. Consider stopping mob spawns");
                } else {
                    Util.staffLog("&cThe TPS is currently: " + Lag.getTPS() + " &6Status: &cBAD. Contact an admin");
                }

            }
        }.runTaskTimer(this, 1, 120 * 20);

        new WorldLoadTask(() -> {

            Core.get().getLogManager().log(Logger.LogType.INFO, "Successfully loaded all worlds!");

        }, this).run();

    }

    private void registerCommands() {
        getCommand("pm").setExecutor(new TeamChatCommand());
        getCommand("pmcoords").setExecutor(new SendCoordsCommand());

    }

    private void registerListeners() {
        for (Listener listener : ListenerHandler.getListeners()) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, this);
        }
    }


    private boolean setupMultiverse() {
        Plugin plugin = getServer().getPluginManager().getPlugin("Multiverse-Core");

        if (plugin instanceof MultiverseCore) {
            multiverseCore = (MultiverseCore) plugin;
            return true;
        }

        return false;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {

    }


    private void setupExtraDatabase() {
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            final String URI = "mongodb://localhost:27017/network";
            MongoClient mongoClient = new MongoClient(new MongoClientURI(URI));

            MongoDatabase mongoDatabase = mongoClient.getDatabase("network");
            this.gameCollection = mongoDatabase.getCollection("uhcGames");
            Core.get().getLogManager().log(Logger.LogType.SERVER, "Successfully connected to Mongo DB!");
        });
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (Commands.getCommands() == null) {
            Commands.setup();
        }

        for (UHCCommand ci : Commands.getCommands()) {
            List<String> cmds = new ArrayList<String>();
            if (ci.getNames() != null) {
                for (String name : ci.getNames()) {
                    cmds.add(name); //Add all the possible aliases
                }
            }

            for (String n : cmds) {
                if (cmd.getName().equalsIgnoreCase(n)) {
                    if (ci.playerOnly()) {
                        if (!(sender instanceof Player)) {
                            sender.sendMessage(ChatUtils.message("&cPlayer only!"));
                            return false;
                        }
                    }

                    if (!(sender instanceof Player)) {
                        try {
                            ci.onCommand(sender, cmd, label, args);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }

                    Player p = (Player) sender;
                    NSPlayer user = NSPlayer.get(p.getUniqueId());
                    if (ci.hasRequiredRank()) {
                        if (!(user.hasRank(ci.getRequiredRank()))) {
                            p.sendMessage(com.nightshadepvp.core.utils.ChatUtils.message("&cYou require the " + ci.getRequiredRank().getPrefix() + "&crank to do this command!"));
                            return false;
                        }
                    }

                    try {
                        ci.onCommand(sender, cmd, label, args);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }


    public static Essentials getEssentials() {
        return ess;
    }

    public static DisguiseAPI getApi() {
        return api;
    }

    public static MultiverseCore getMultiverseCore() {
        return multiverseCore;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public MongoCollection<Document> getGameCollection() {
        return gameCollection;
    }

    private void hideEnchants() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new EnchantHider(this));
    }

}
