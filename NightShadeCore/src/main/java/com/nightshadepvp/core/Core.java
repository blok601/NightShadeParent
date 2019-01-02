package com.nightshadepvp.core;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.massivecraft.massivecore.MassivePlugin;
import com.massivecraft.massivecore.store.DriverFlatfile;
import com.massivecraft.massivecore.store.DriverMongo;
import com.nightshadepvp.core.entity.MConf;
import com.nightshadepvp.core.entity.NSPlayerColl;
import com.nightshadepvp.core.listener.LiteBansListener;
import com.nightshadepvp.core.punishment.PunishmentHandler;
import com.nightshadepvp.core.store.NSStore;
import com.nightshadepvp.core.store.NSStoreConf;
import com.nightshadepvp.core.ubl.UBLHandler;
import com.nightshadepvp.core.utils.ChatUtils;
import litebans.api.Events;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import redis.clients.jedis.Jedis;

public class Core extends MassivePlugin implements PluginMessageListener {

    private String matchpost = "uhc.gg";

    private static Core i;

    public Core() {
        Core.i = this;
    }

    private static Scoreboard board;
    private UBLHandler ublHandler = new UBLHandler(this);

    private Logger logger;


    private Jedis jedis;


    @Override
    public void onEnableInner() {
        NSStoreConf.get().load();
        NSStore.registerDriver(DriverMongo.get());
        NSStore.registerDriver(DriverFlatfile.get());

        this.activateAuto();

        logger = new Logger();
        board = Bukkit.getScoreboardManager().getMainScoreboard();

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "staffchat");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "staffchat", this);

        getConfig().options().copyDefaults(true);
        saveConfig();
        Events.get().register(new LiteBansListener());

        PunishmentHandler.getInstance().setup();
        ServerType.setType(MConf.get().serverType);

        new BukkitRunnable(){
            @Override
            public void run() {
                jedis = new Jedis("localhost");
                //It will be set by: key: random code generator value: discord unique ID
            }
        }.runTaskAsynchronously(this);

        new BukkitRunnable(){
            @Override
            public void run() {
                NSPlayerColl.get().getAllOnline().forEach(nsPlayer -> {
                    nsPlayer.setCurrentAFKTime(nsPlayer.getCurrentAFKTime() + 1);
                    if(nsPlayer.getCurrentAFKTime() >= 10){
                        nsPlayer.setAFK(true); //If they haven't done shit for 10 seconds they are afk
                    }
                });
            }
        }.runTaskTimer(this, 0, 20);


        getLogManager().log(Logger.LogType.INFO, "Starting UBL Tasks...");
        ublHandler.setup();
        Bukkit.getConsoleSender().sendMessage(ChatUtils.message("&eLoading NightShadeCore version: " + getDescription().getVersion() + ". Server Type: " + ServerType.getType().toString()));
    }


    @Override
    public void onDisable() {
        i = null;
    }


    public static Core get() {
        return i;
    }

    public Logger getLogManager() {
        return this.logger;
    }


    /**
     * A method that will be thrown when a PluginMessageSource sends a plugin
     * message on a registered channel.
     *
     * @param channel Channel that the message was sent through.
     * @param player  Source of the message.
     * @param message The raw message that was sent.
     */
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (channel.equalsIgnoreCase("staffchat")) {
            ByteArrayDataInput in = ByteStreams.newDataInput(message);
            String playerName = in.readUTF();
            //String server = in.readUTF();
            String msg = in.readUTF();

            NSPlayerColl.get().getAllOnline().stream().filter(nsPlayer -> nsPlayer.hasRank(Rank.TRIAL)).forEach(nsPlayer -> nsPlayer.msg(ChatUtils.format("&8[&cStaff Chat&8] &a" + playerName + "&8: &r" + msg)));
        }else if(channel.equalsIgnoreCase("BungeeCord")){
            ByteArrayDataInput in = ByteStreams.newDataInput(message);
            String playerName = in.readUTF();
            boolean state = in.readBoolean();

            if(MConf.get().isMaintenance() == state) return;

            MConf.get().setMaintenance(state);

            if(state){
                if(!MConf.get().isMaintenance()){
                    Bukkit.broadcastMessage(ChatUtils.format("&5Proxy&8 » &b" + playerName + " &ahas enabled maintenance mode!"));
                    Bukkit.broadcastMessage(ChatUtils.format("&5Proxy&8 » &aThe server will enter maintenance mode in 5 seconds..."));
                    MConf.get().setMaintenance(true);
                    MConf.get().changed();
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            NSPlayerColl.get().getAllOnline().stream().filter(nsPlayer -> !nsPlayer.hasRank(Rank.TRIAL)).forEach(nsPlayer -> {
                                nsPlayer.getPlayer().kickPlayer("§cThe Server is under maintenance!");
                                Bukkit.broadcastMessage(ChatUtils.format("&5Proxy&8 » &bMaintenance Mode &aenabled!"));
                            });
                        }
                    }.runTaskLater(this, 100);
                }
            }else{
                if(MConf.get().isMaintenance()){
                    Bukkit.broadcastMessage(ChatUtils.format("&5Proxy&8 » &b" + playerName + " &chas disabled maintenance mode!"));
                    Bukkit.broadcastMessage(ChatUtils.format("&5Proxy&8 » &cThe server will exit maintenance mode..."));
                    MConf.get().setMaintenance(false);
                    MConf.get().changed();
                    Bukkit.broadcastMessage(ChatUtils.format("&5Proxy&8 » &bMaintenance Mode &cdisabled!"));

                }
            }
        }
    }

    public Jedis getJedis() {
        if(jedis == null || !jedis.isConnected()){
            jedis = new Jedis("localhost");
        }

        return jedis;
    }

    public String getMatchpost() {
        return matchpost;
    }

    public void setMatchpost(String matchpost) {
        this.matchpost = matchpost;
    }

    public UBLHandler getUblHandler() {
        return ublHandler;
    }


}
