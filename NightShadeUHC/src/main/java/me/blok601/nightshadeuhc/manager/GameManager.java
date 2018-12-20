package me.blok601.nightshadeuhc.manager;


import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import com.wimbli.WorldBorder.BorderData;
import lombok.Getter;
import lombok.Setter;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.object.CachedColor;
import me.blok601.nightshadeuhc.entity.object.PlayerRespawnObject;
import me.blok601.nightshadeuhc.entity.object.SetupStage;
import me.blok601.nightshadeuhc.task.*;
import me.blok601.nightshadeuhc.task.TimerTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Blok on 8/5/2017.
 */
public class GameManager {

    /*
    This class is ALL static and is only used for storage purposes
     */

    private static GameManager i = new GameManager();
    public static GameManager get() {
        return i;
    }

    private  HashMap<UUID, PlayerRespawnObject> invs = new HashMap<>();
    private  ArrayList<UUID> deathBans = new ArrayList<>();
    private  HashSet<CachedColor> colors;//storing player names because teams don't store UUIDs ;(
    private  ArrayList<String> whitelist = new ArrayList<>();
    private  HashSet<String> respawnQueue = new HashSet<>(); // This saves names like whitelist and will check using ignore case
    @Getter
    private  HashMap<UUID, Integer> helpOpMutes = new HashMap<>();
    @Getter
    public HashMap<UUID, String> helpopMuteReasons = new HashMap<>();
    @Getter
    public HashSet<String> lateScatter = new HashSet<>();
    public boolean IS_SCATTERING = false;
    private  TimerTask timer;
    private  boolean whitelistEnabled;
    private  int finalHealTime = 0;
    private  int pvpTime = 0;
    private  int borderTime = 0;
    @Getter
    @Setter
    private  int meetupTime = 0;
    private  int radius = 0;
    private  FinalHealTask finalHealTask;
    private  PvPTask pvpTask;
    private  WorldBorderTask worldBorderTask;
    @Setter
    @Getter
    private  MeetupTask meetupTask;
    private  int[] shrinks;
    private  int borderID;
    private  String serverType;
    private  HashMap<UUID, Integer> kills = new HashMap<>();
    @Getter
    public World world;
    private  String date;
    @Getter
    private HashMap<Player, SetupStage> setupStageHashMap = new HashMap<>();
    @Getter@Setter
    private int setupRadius = 0;
    @Getter@Setter
    private int setupNetherRadius = 0;
    @Getter@Setter
    private String setupSeed = "";
    @Getter@Setter
    private boolean netherEnabled = false;
    @Getter@Setter
    private boolean overWorldPregenned = false;
    @Getter@Setter
    private boolean netherPregenned = false;
    @Getter@Setter
    private World netherWorld = null;

    // ---------------------------------------
    //            Host GUI Game Settings
    // ---------------------------------------

    @Getter
    @Setter
    private int starterFood = 10;
    private int maxPlayers;
    private Player host;
    private boolean isTeam;
    private double appleRates = 5;
    private double flintRates = 50;


    public void setDate(){
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy:HH");
        Date date = new Date();
        this.date = format.format(date);
    }
    public int getFinalHealTime() {
        return finalHealTime;
    }

    public void setFinalHealTime(int finalHealTime) {
        this.finalHealTime = finalHealTime;
    }

    public int getPvpTime() {
        return pvpTime;
    }

    public void setPvpTime(int pvpTime) {
        this.pvpTime = pvpTime;
    }

    public int getBorderTime() {
        return borderTime;
    }

    public void setBorderTime(int borderTime) {
        this.borderTime = borderTime;
    }

    public boolean isIsTeam() {
        return isTeam;
    }

    public void setIsTeam(boolean isTeam) {
        this.isTeam = isTeam;
    }

    public Player getHost() {
        return host;
    }

    public void setHost(Player host) {
        this.host = host;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getFirstShrink() {
        return getShrinks()[0];
    }

    public void setFirstShrink(int firstShrink) {

        getShrinks()[0] = firstShrink;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getWhitelist() {
        return whitelist;
    }

    public boolean isWhitelistEnabled() {
        return whitelistEnabled;
    }

    public void setWhitelistEnabled(boolean whitelistEnabled) {
        this.whitelistEnabled = whitelistEnabled;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public TimerTask getTimer() {
        return timer;
    }

    public void setTimer(TimerTask timer) {
        this.timer = timer;
    }

    public double getBorderSize(){

        if(world == null) return 0;

        BorderData bd = com.wimbli.WorldBorder.WorldBorder.plugin.getWorldBorder(world.getName());

        return bd.getRadiusX();
    }

    public HashMap<UUID, PlayerRespawnObject> getInvs() {
        return invs;
    }


    public void setup(){
        setTimer(new TimerTask());
        setMaxPlayers(60);

        shrinks = new int[]{
                500, 300, 200, 100, 50, 25, 10, 0, 0
        };

        colors = new HashSet<>();
        Core.get().getLogManager().log(Logger.LogType.INFO, "GameManager has been setup!");
    }


    public ArrayList<UUID> getDeathBans() {
        return deathBans;
    }

    public double getAppleRates() {
        return appleRates;
    }

    public void setAppleRates(double appleRates) {
        this.appleRates = appleRates;
    }

    public double getFlintRates() {
        return flintRates;
    }

    public void setFlintRates(double flintRates) {
        this.flintRates = flintRates;
    }

    public FinalHealTask getFinalHealTask() {
        return finalHealTask;
    }

    public void setFinalHealTask(FinalHealTask finalHealTask) {
        this.finalHealTask = finalHealTask;
    }

    public PvPTask getPvpTask() {
        return pvpTask;
    }

    public void setPvpTask(PvPTask pvpTask) {
        this.pvpTask = pvpTask;
    }

    public WorldBorderTask getWorldBorderTask() {
        return worldBorderTask;
    }

    public void setWorldBorderTask(WorldBorderTask worldBorderTask) {
        this.worldBorderTask = worldBorderTask;
    }

    public int[] getShrinks() {
        return shrinks;
    }

    public int getBorderID() {
        return borderID;
    }

    public void setBorderID(int borderID) {
        this.borderID = borderID;
    }

    public HashMap<UUID, Integer> getKills() {
        return kills;
    }

    public void genWalls(int size) {
        UHC.get().getServer().dispatchCommand(Bukkit.getConsoleSender(), "wb " + getWorld().getName() + " set " + size + " " + size + " 0 0");
        UHC.get().getServer().dispatchCommand(Bukkit.getConsoleSender(), "wb shape square");
        Location loc = new Location(getWorld(), 0.0D, 59.0D, 0.0D);
        World uhc = getWorld();
        int i = 4;
        while (i < 4 + 4) {
            for (int x = loc.getBlockX() - size; x <= loc.getBlockX() + size; x++) {
                for (int y = 59; y <= 59; y++) {
                    for (int z = loc.getBlockZ() - size; z <= loc.getBlockZ() + size; z++) {
                        if ((x == loc.getBlockX() - size) || (x == loc.getBlockX() + size) || (z == loc.getBlockZ() - size) || (z == loc.getBlockZ() + size)) {
                            Location loc2 = new Location(getWorld(), x, y, z);
                            loc2.setY(uhc.getHighestBlockYAt(loc2));
                            loc2.getBlock().setType(Material.BEDROCK);
                        }
                    }
                }
            }
            i++;
        }
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public HashSet<CachedColor> getColors() {
        return colors;
    }

    public HashSet<String> getRespawnQueue() {
        return respawnQueue;
    }
}
