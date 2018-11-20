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
import me.blok601.nightshadeuhc.commands.CmdInterface;
import me.blok601.nightshadeuhc.commands.Commands;
import me.blok601.nightshadeuhc.listeners.gui.EnchantHider;
import me.blok601.nightshadeuhc.listeners.modules.ComponentHandler;
import me.blok601.nightshadeuhc.listeners.modules.GoldenHeadRecipe;
import me.blok601.nightshadeuhc.logger.LoggerHandler;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.manager.ListenerHandler;
import me.blok601.nightshadeuhc.manager.Settings;
import me.blok601.nightshadeuhc.packet.OldEnchanting;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.scoreboard.PlayerScoreboard;
import me.blok601.nightshadeuhc.scoreboard.ScoreboardManager;
import me.blok601.nightshadeuhc.stats.handler.StatsHandler;
import me.blok601.nightshadeuhc.tasks.ScoreboardHealthTask;
import me.blok601.nightshadeuhc.tasks.StaffTrackTask;
import me.blok601.nightshadeuhc.tasks.WorldLoadTask;
import me.blok601.nightshadeuhc.teams.CmdSendCoords;
import me.blok601.nightshadeuhc.teams.CmdTeamChat;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.Lag;
import me.blok601.nightshadeuhc.utils.Util;
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

    @Override
    public void onEnableInner() {

        this.activateAuto();

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        getConfig().options().copyDefaults(true);
        saveConfig();

        GameState.setState(GameState.WAITING);
        Settings.getInstance().setup(this);

        registerCommands();
        registerListeners();

        setupExtraDatabase();
        GameManager.setup();
        scoreboardManager = new ScoreboardManager();
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            scoreboardManager.getPlayerScoreboards().values().forEach(PlayerScoreboard::update);
        }, 0L, 35L);
        new ScoreboardHealthTask(scoreboardManager).runTaskTimerAsynchronously(this, 0, 60);
        Commands.setup();
        new StaffTrackTask().runTaskTimer(this, 0, 100);

        ComponentHandler.getInstance().setup();
        StatsHandler.getInstance().setup();

        sm = new ScenarioManager();
        api = getServer().getServicesManager().getRegistration(DisguiseAPI.class).getProvider();
        ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
        if(!setupMultiverse()){
            Core.get().getLogManager().log(Logger.LogType.SEVERE, "Multiverse wasn't found! Disabling plugin!");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        new GoldenHeadRecipe();
        sm.setup();

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

        if (Bukkit.getPluginManager().getPlugin("ViaRewind") != null) {
            //UHC2
            GameManager.setServerType("UHC2");
        } else {
            GameManager.setServerType("UHC1");
        }


        if(GameManager.getServerType().equalsIgnoreCase("UHC2")){
            hideEnchants();
            new OldEnchanting(this);
        }

        Bukkit.getConsoleSender().sendMessage(ChatUtils.message("&aNightShadePvPUHC " + getDescription().getVersion() + " has been successfully enabled!"));
        Bukkit.getConsoleSender().sendMessage(ChatUtils.message("&eDetected Server&8: &3" + GameManager.getServerType()));

    }

    public void onDisable() {
        //LoggerHandler.getInstance().getLoggers().forEach(combatLogger -> LoggerHandler.getInstance().removeLogger(combatLogger));
        LoggerHandler.getInstance().getLoggers().clear();
        Bukkit.getMessenger().unregisterIncomingPluginChannel(this, "BungeeCord", this);
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");

    }

    private void registerCommands() {
        getCommand("pm").setExecutor(new CmdTeamChat());
        getCommand("pmcoords").setExecutor(new CmdSendCoords());

    }

    private void registerListeners() {
        for (Listener listener : ListenerHandler.getListeners()) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (Commands.getCommands() == null) {
            Commands.setup();
        }

        for (CmdInterface ci : Commands.getCommands()) {
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

                    if(!(sender instanceof Player)){
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


    /**
     * A method that will be thrown when a PluginMessageSource sends a i
     * message on a registered channel.
     *
     * @param channel Channel that the message was sent through.
     * @param player  Source of the message.
     * @param message The raw message that was sent.
     */
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {

    }

    private boolean setupMultiverse() {
        Plugin plugin = getServer().getPluginManager().getPlugin("Multiverse-Core");

        if (plugin instanceof MultiverseCore) {
            multiverseCore = (MultiverseCore) plugin;
            return true;
        }

        return false;
    }

    public static MultiverseCore getMultiverseCore() {
        return multiverseCore;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
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

    public MongoCollection<Document> getGameCollection() {
        return gameCollection;
    }

    private void hideEnchants(){
        ProtocolLibrary.getProtocolManager().addPacketListener(new EnchantHider(this));
    }

}
