package me.blok601.nightshadeuhc.manager;


import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import com.wimbli.WorldBorder.BorderData;
import lombok.Getter;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.object.CachedColor;
import me.blok601.nightshadeuhc.entity.object.PlayerRespawnObject;
import me.blok601.nightshadeuhc.tasks.FinalHealTask;
import me.blok601.nightshadeuhc.tasks.PvPTask;
import me.blok601.nightshadeuhc.tasks.TimerTask;
import me.blok601.nightshadeuhc.tasks.WorldBorderTask;
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

    private static HashMap<UUID, PlayerRespawnObject> invs = new HashMap<>();
    private static ArrayList<UUID> deathBans = new ArrayList<>();
    private static HashSet<CachedColor> colors;//storing player names because teams don't store UUIDs ;(

    private static int maxPlayers;

    private static ArrayList<String> whitelist = new ArrayList<>();
    private static HashSet<String> respawnQueue = new HashSet<>(); // This saves names like whitelist and will check using ignore case

    @Getter
    private static HashMap<UUID, Integer> helpOpMutes = new HashMap<>();
    @Getter
    public static HashMap<UUID, String> helpopMuteReasons = new HashMap<>();
    @Getter
    public static HashSet<String> lateScatter = new HashSet<>();

    public static boolean IS_SCATTERING = false;


    private static TimerTask timer;

    private static boolean whitelistEnabled;

    private static int finalHealTime = 0;

    private static int pvpTime = 0;

    private static int borderTime = 0;

    private static boolean isTeam;

    private static Player host;
    private static int radius = 0;
    private static int appleRates = 5;
    private static int flintRates = 50;

    private static FinalHealTask finalHealTask;
    private static PvPTask pvpTask;
    private static WorldBorderTask worldBorderTask;

    private static int[] shrinks;
    private static int borderID;

    private static String serverType;

    private static HashMap<UUID, Integer> kills = new HashMap<>();

    @Getter
    public static World world;

    private static String date;

    public static String getDate(){
        return date;
    }

    public static void setDate(){
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy:HH");
        Date date = new Date();
        GameManager.date = format.format(date);
    }

    public static int getFinalHealTime() {
        return finalHealTime;
    }

    public static void setFinalHealTime(int finalHealTime) {
        GameManager.finalHealTime = finalHealTime;
    }

    public static int getPvpTime() {
        return pvpTime;
    }

    public static void setPvpTime(int pvpTime) {
        GameManager.pvpTime = pvpTime;
    }

    public static int getBorderTime() {
        return borderTime;
    }

    public static void setBorderTime(int borderTime) {
        GameManager.borderTime = borderTime;
    }

    public static boolean isIsTeam() {
        return isTeam;
    }

    public static void setIsTeam(boolean isTeam) {
        GameManager.isTeam = isTeam;
    }

    public static Player getHost() {
        return host;
    }

    public static void setHost(Player host) {
        GameManager.host = host;
    }

    public static int getRadius() {
        return radius;
    }

    public static void setRadius(int radius) {
        GameManager.radius = radius;
    }

    public static int getFirstShrink() {
        return getShrinks()[0];
    }

    public static void setFirstShrink(int firstShrink) {

        getShrinks()[0] = firstShrink;
    }

    public static void setWorld(World world) {
        GameManager.world = world;
    }

    public static void setDate(String date) {
        GameManager.date = date;
    }

    public static ArrayList<String> getWhitelist() {
        return whitelist;
    }

    public static boolean isWhitelistEnabled() {
        return whitelistEnabled;
    }

    public static void setWhitelistEnabled(boolean whitelistEnabled) {
        GameManager.whitelistEnabled = whitelistEnabled;
    }

    public static int getMaxPlayers() {
        return maxPlayers;
    }

    public static void setMaxPlayers(int maxPlayers) {
        GameManager.maxPlayers = maxPlayers;
    }

    public static TimerTask getTimer() {
        return timer;
    }

    public static void setTimer(TimerTask timer) {
        GameManager.timer = timer;
    }

    public static double getBorderSize(){

        if(world == null) return 0;

        BorderData bd = com.wimbli.WorldBorder.WorldBorder.plugin.getWorldBorder(world.getName());

        return bd.getRadiusX();
    }

    public static HashMap<UUID, PlayerRespawnObject> getInvs() {
        return invs;
    }


    public static void setup(){
        setTimer(new TimerTask());
        setMaxPlayers(60);

        shrinks = new int[]{
                500, 300, 200, 100, 50, 25, 10, 0, 0
        };

        colors = new HashSet<>();
        Core.get().getLogManager().log(Logger.LogType.INFO, "GameManager has been setup!");

    }


    public static ArrayList<UUID> getDeathBans() {
        return deathBans;
    }

    public static int getAppleRates() {
        return appleRates;
    }

    public static void setAppleRates(int appleRates) {
        GameManager.appleRates = appleRates;
    }

    public static int getFlintRates() {
        return flintRates;
    }

    public static void setFlintRates(int flintRates) {
        GameManager.flintRates = flintRates;
    }

    public static FinalHealTask getFinalHealTask() {
        return finalHealTask;
    }

    public static void setFinalHealTask(FinalHealTask finalHealTask) {
        GameManager.finalHealTask = finalHealTask;
    }

    public static PvPTask getPvpTask() {
        return pvpTask;
    }

    public static void setPvpTask(PvPTask pvpTask) {
        GameManager.pvpTask = pvpTask;
    }

    public static WorldBorderTask getWorldBorderTask() {
        return worldBorderTask;
    }

    public static void setWorldBorderTask(WorldBorderTask worldBorderTask) {
        GameManager.worldBorderTask = worldBorderTask;
    }

    public static int[] getShrinks() {
        return shrinks;
    }

    public static int getBorderID() {
        return borderID;
    }

    public static void setBorderID(int borderID) {
        GameManager.borderID = borderID;
    }

    public static HashMap<UUID, Integer> getKills() {
        return kills;
    }

    public static void genWalls(int size) {
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

    public static String getServerType() {
        return serverType;
    }

    public static void setServerType(String serverType) {
        GameManager.serverType = serverType;
    }

    public static HashSet<CachedColor> getColors() {
        return colors;
    }

    public static HashSet<String> getRespawnQueue() {
        return respawnQueue;
    }
}
