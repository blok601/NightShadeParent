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
import com.onarandombox.MultiverseCore.MultiverseCore;
import de.robingrether.idisguise.api.DisguiseAPI;
import me.blok601.nightshadeuhc.command.CommandHandler;
import me.blok601.nightshadeuhc.component.ComponentHandler;
import me.blok601.nightshadeuhc.component.GoldenHeadRecipe;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.listener.gui.EnchantHider;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.manager.ListenerHandler;
import me.blok601.nightshadeuhc.manager.LoggerManager;
import me.blok601.nightshadeuhc.manager.SettingsManager;
import me.blok601.nightshadeuhc.packet.OldEnchanting;
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
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.UUID;

public class UHC extends MassivePlugin implements PluginMessageListener {

    private static UHC i;

    public UHC() {
        UHC.i = this;
    }

    public static UHC get() {
        return i;
    }

    private Essentials ess;
    private DisguiseAPI api;
    private MultiverseCore multiverseCore;

    private ScenarioManager scenarioManager;
    private ScoreboardManager scoreboardManager;
    private ListenerHandler listenerHandler;
    private CommandHandler commandHandler;
    private ComponentHandler componentHandler;
    private GameManager gameManager;


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

        api = getServer().getServicesManager().getRegistration(DisguiseAPI.class).getProvider();
        ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");

        setupExtraDatabase();
        setupTasks();

        getConfig().options().copyDefaults(true);
        saveConfig();
        SettingsManager.getInstance().setup(this);
        StatsHandler.getInstance().setup();
        new GoldenHeadRecipe();


        this.gameManager = new GameManager();
        this.scoreboardManager = new ScoreboardManager();
        this.scenarioManager = new ScenarioManager();
        scenarioManager.setup();
        this.componentHandler = new ComponentHandler(GameManager.get(), scenarioManager);
        this.componentHandler.setup();
        this.commandHandler = new CommandHandler(this, GameManager.get(), scenarioManager);
        this.listenerHandler = new ListenerHandler(this, Core.get(), scenarioManager, GameManager.get(), componentHandler);
        this.listenerHandler.complete();


        Bukkit.getConsoleSender().sendMessage(ChatUtils.message("&aNightShadePvPUHC " + getDescription().getVersion() + " has been successfully enabled!"));
        Bukkit.getConsoleSender().sendMessage(ChatUtils.message("&eDetected Server&8: &3" + GameManager.get().getServerType()));
        GameState.setState(GameState.WAITING);
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

    private void hideEnchants() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new EnchantHider(this));
    }

    public static Essentials getEssentials() {
        return get().getEss();
    }

    public static DisguiseAPI getApi() {
        return get().getDisguiseAPI();
    }

    public static MultiverseCore getMultiverseCore() {
        return get().getMultiverseCorePlugin();
    }

    public Essentials getEss() {
        return ess;
    }

    public DisguiseAPI getDisguiseAPI() {
        return api;
    }

    public MultiverseCore getMultiverseCorePlugin() {
        return multiverseCore;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public MongoCollection<Document> getGameCollection() {
        return gameCollection;
    }



}
