package me.blok601.nightshadeuhc.entity;

import com.google.gson.JsonObject;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.store.SenderEntity;
import com.massivecraft.massivecore.util.MUtil;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.command.staff.SpectatorCommand;
import me.blok601.nightshadeuhc.entity.object.ArenaSession;
import me.blok601.nightshadeuhc.entity.object.PlayerStatus;
import me.blok601.nightshadeuhc.scoreboard.PlayerScoreboard;
import me.blok601.nightshadeuhc.scoreboard.provider.type.ArenaProvider;
import me.blok601.nightshadeuhc.scoreboard.provider.type.UHCProvider;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import me.blok601.nightshadeuhc.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UHCPlayer extends SenderEntity<UHCPlayer> {
    // -------------------------------------------- //
    // META
    // -------------------------------------------- //

    public static UHCPlayer get(Object oid) {
        return UHCPlayerColl.get().get(oid);
    }

    // -------------------------------------------- //
    // LOAD
    // -------------------------------------------- //

    private int blocksMined = 0;
    private int diamondsMined = 0;
    private int coalMined = 0;
    private int goldMined = 0;
    private int ironMined = 0;
    private int oresMined = 0;
    private int lapisMined = 0;
    private int emeraldsMined = 0;
    private int gamesPlayed = 0;
    private int gamesWon = 0;
    private int kills = 0;
    private int deaths = 0;
    private double points = 0;
    private int level = 0;

    private PS lastLocation = null;

    // -----------------------------
    //        ARENA STATS
    // -----------------------------
    private int arenaKills = 0;
    private int arenaDeaths = 0;
    private int highestArenaKillStreak = 0;
    private ArrayList<String> pastArenaSessions = new ArrayList<>();
    private int arenaSwordHits = 0;
    private int arenaSwordSwings = 0;
    private int arenaGapplesEaten = 0;
    private int arenaBowAttempts = 0;
    private int arenaBowHits = 0;
    private transient ArenaSession arenaSession;


    private transient boolean combatTagged;
    private transient boolean isDisguised;
    private transient String disguisedName;
    private transient boolean isSpectator;
    private transient boolean staffMode;
    private transient boolean vanished;
    private transient boolean noClean;
    private transient int noCleanTimer;
    private transient boolean receiveHelpop;
    private transient boolean frozen;
    private transient boolean receivingToggleSneakAlerts;
    private transient boolean receivingSpectatorInfo;
    private transient boolean receivingMiningAlerts;
    private transient boolean receivingCommandSpy;
    private transient double changedLevel;
    private transient boolean usingOldVersion;
    private transient PlayerStatus playerStatus;

    @Override
    public UHCPlayer load(UHCPlayer that) {

        //super.load(that);

        this.setBlocksMined(that.blocksMined);
        this.setDiamondsMined(that.diamondsMined);
        this.setCoalMined(that.coalMined);
        this.setGoldMined(that.goldMined);
        this.setIronMined(that.ironMined);
        this.setOresMined(that.oresMined);
        this.setLapisMined(that.lapisMined);
        this.setEmeraldsMined(that.emeraldsMined);
        this.setArenaSwordHits(that.arenaSwordHits);
        this.setArenaSwordSwings(that.arenaSwordSwings);
        this.setArenaGapplesEaten(that.arenaGapplesEaten);
        this.setArenaBowAttempts(that.arenaBowAttempts);
        this.setArenaBowHits(that.arenaBowHits);

        this.setGamesPlayed(that.gamesPlayed);
        this.setGamesWon(that.gamesWon);
        this.setKills(that.kills);
        this.setDeaths(that.deaths);
        this.setPoints(that.points);
        this.setLevel(that.level);
        this.setArenaKills(that.arenaKills);
        this.setArenaDeaths(that.arenaDeaths);
        this.setHighestArenaKillStreak(that.highestArenaKillStreak);
        this.setPastArenaSessions(that.pastArenaSessions);


        this.setLastLocation(that.lastLocation);

        return this;
    }

    public void handleBlockBreak(Block block){
        if(block.getType() == Material.DIAMOND_ORE){
            this.diamondsMined++;
            addPoints(0.1);
            this.setChangedLevel(this.getChangedLevel() + 0.5);
        }else if(block.getType() == Material.GOLD_ORE){
            this.goldMined++;
            this.setChangedLevel(this.getChangedLevel() + 0.25);
        }else if(block.getType() == Material.IRON_ORE){
            this.ironMined++;
            this.setChangedLevel(this.getChangedLevel() + 0.1);
        }else if(block.getType() == Material.COAL_ORE){
            this.coalMined++;
        }else if(block.getType() == Material.EMERALD_ORE){
            this.emeraldsMined++;
        }else if(block.getType() == Material.LAPIS_ORE){
            this.lapisMined++;
        }

        this.blocksMined++;
    }


    public String getKD(){
        DecimalFormat format = new DecimalFormat("##.##");
        if(getDeaths() == 0){
            return format.format(getKills());
        }
        double kd = getKills()/(getDeaths() * 1.0);
        return format.format(kd);
    }

    public String getWG(){
        DecimalFormat format = new DecimalFormat("##.##");
        if(getGamesPlayed() == 0){
            return format.format(getGamesWon());
        }
        double wg = getGamesWon()/(getGamesPlayed() * 1.0);
        return format.format(wg);
    }

    public int getBlocksMined() {
        return blocksMined;
    }

    public void setBlocksMined(int blocksMined) {
        this.blocksMined = blocksMined;
    }

    public int getDiamondsMined() {
        return diamondsMined;
    }

    public void setDiamondsMined(int diamondsMined) {
        this.diamondsMined = diamondsMined;
    }

    public int getCoalMined() {
        return coalMined;
    }

    public void setCoalMined(int coalMined) {
        this.coalMined = coalMined;
    }

    public int getGoldMined() {
        return goldMined;
    }

    public void setGoldMined(int goldMined) {
        this.goldMined = goldMined;
    }

    public int getIronMined() {
        return ironMined;
    }

    public void setIronMined(int ironMined) {
        this.ironMined = ironMined;
    }

    public int getOresMined() {
        return getDiamondsMined() + getGoldMined() + getIronMined() + getCoalMined() + getEmeraldsMined() + getLapisMined();
    }

    public void setOresMined(int oresMined) {
        this.oresMined = oresMined;
    }

    public int getLapisMined() {
        return lapisMined;
    }

    public void setLapisMined(int lapisMined) {
        this.lapisMined = lapisMined;
    }

    public int getEmeraldsMined() {
        return emeraldsMined;
    }

    public void setEmeraldsMined(int emeraldsMined) {
        this.emeraldsMined = emeraldsMined;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void addKill(int amt){
        this.kills = this.kills + amt;
        this.changed();
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public void addPoints(double points){
        this.points = points + points;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public PS getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(PS lastLocation) {
        this.lastLocation = lastLocation;
    }

    public boolean isCombatTagged() {
        return combatTagged;
    }

    public void setCombatTagged(boolean combatTagged) {
        this.combatTagged = combatTagged;
    }

    public boolean isDisguised() {
        return isDisguised;
    }

    public void setDisguised(boolean disguised) {
        isDisguised = disguised;
    }

    public String getDisguisedName() {
        return disguisedName;
    }

    public void setDisguisedName(String disguisedName) {
        this.disguisedName = disguisedName;
    }

    public boolean isSpectator() {
        return isSpectator;
    }

    public void setSpectator(boolean spectator) {
        isSpectator = spectator;
    }

    public boolean isNoClean() {
        return noClean;
    }

    public void setNoClean(boolean noClean) {
        this.noClean = noClean;
    }

    public int getNoCleanTimer() {
        return noCleanTimer;
    }

    public void setNoCleanTimer(int noCleanTimer) {
        this.noCleanTimer = noCleanTimer;
    }

    public boolean isReceiveHelpop() {
        return receiveHelpop;
    }

    public void setReceiveHelpop(boolean receiveHelpop) {
        this.receiveHelpop = receiveHelpop;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public boolean isUsingOldVersion() {
        return usingOldVersion;
    }

    public double getPointsAverage() {
        if (this.gamesPlayed == 0) {
            return this.points;
        }

        if (this.points == 0) {
            return 0;
        }

        return this.points / this.gamesPlayed;
    }

    public void setUsingOldVersion(boolean usingOldVersion) {
        this.usingOldVersion = usingOldVersion;
    }
// -------------------------------------------- //
    // FIELD: lastActivityMillis
    // -------------------------------------------- //

    private long lastActivityMillis = System.currentTimeMillis();

    public long getLastActivityMillis() {
        return this.lastActivityMillis;
    }

    public void setLastActivityMillis(long lastActivityMillis) {
        // Clean input
        long target = lastActivityMillis;

        // Detect Nochange
        if (MUtil.equals(this.lastActivityMillis, target)) return;

        // Apply
        this.lastActivityMillis = target;

        // Mark as changed
        this.changed();
    }

    public void setLastActivityMillis() {
        this.setLastActivityMillis(System.currentTimeMillis());
    }


    public void spec(){
        if(!isOnline()) return;
        setSpectator(true);
        setPlayerStatus(PlayerStatus.SPECTATING);
        Player p = getPlayer();

        NSPlayer user;
        UHCPlayerColl.get().getAllPlaying()
                .stream()
                .filter(SenderEntity::isPlayer)
                .forEach(uhcPlayer -> uhcPlayer.getPlayer().hidePlayer(p));
        //Hid them from players -> show other specs
        UHCPlayerColl.get().getSpectators().forEach(uhcPlayer -> p.showPlayer(uhcPlayer.getPlayer()));


        user = NSPlayer.get(p.getUniqueId());

        if(user.hasRank(Rank.TRIAL)){
            if(!isStaffMode()){
                staffMode();
            }
        }

        p.setGameMode(GameMode.CREATIVE);
        p.setAllowFlight(true);
        p.setFlying(true);
        p.setFlySpeed(0.2F);
        p.sendMessage(ChatUtils.message("&6You are now a spectator!"));
        Util.staffLog("&2" + p.getName()+ " is now a spectator!");
    }

    public void unspec(){
        if(!isOnline()) return;
        if(!isSpectator()) return;
        setSpectator(false);
        this.playerStatus = PlayerStatus.LOBBY;
        Player p = getPlayer();

        for (Player pl : Bukkit.getOnlinePlayers()){
            pl.showPlayer(p);
        }

        UHCPlayerColl.get().getSpectators().forEach(uhcPlayer -> p.hidePlayer(uhcPlayer.getPlayer())); //Re-hide spectators -- abundant
        p.setFlying(false);
        p.setAllowFlight(false);
        p.setGameMode(GameMode.SURVIVAL);
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        p.sendMessage(ChatUtils.message("&5You are no longer a spectator!"));
        Util.staffLog("&2" + p.getName()+ " is no longer a spectator!");
    }

    public boolean isInArena() {
        return this.playerStatus == PlayerStatus.ARENA;
    }

    public boolean isStaffMode() {
        return staffMode;
    }

    public void setStaffMode(boolean staffMode) {
        this.staffMode = staffMode;
    }

    public void staffMode(){
        Player player = getPlayer();
        setStaffMode(true);
        player.getInventory().clear();
        ItemStack jump = new ItemBuilder(Material.COMPASS).name(ChatUtils.format("&cJump Tool")).make();

        ItemStack vanish = new ItemBuilder(Material.TORCH).name(ChatUtils.format("&cToggle Vanish")).make();

        ItemStack randomHead = new ItemStack(Material.SKULL_ITEM, 1,  (short) 3);
        ItemStack randomBuilder = new ItemBuilder(randomHead).name("&cRandom Teleport").make();

        ItemStack inspect = new ItemBuilder(Material.BOOK).name(ChatUtils.format("&cPlayer Inventory")).make();

        if (!isSpectator()) {
            SpectatorCommand.setSpec(player);
            player.sendMessage(ChatUtils.message("&aYou are now a spectator"));
        }

        player.chat("/van");
        UHCPlayerColl.get().getAllOnline().stream().filter(uhcPlayer -> !uhcPlayer.isSpectator).forEach(uhcPlayer -> uhcPlayer.getPlayer().hidePlayer(player));
        this.vanish(false);

        player.getInventory().setItem(0, jump);
        player.getInventory().setItem(3, vanish);
        player.getInventory().setItem(5, randomBuilder);
        player.getInventory().setItem(8, inspect);

        player.setGameMode(GameMode.CREATIVE);
        player.setAllowFlight(true);
        player.setFlying(true);
        player.setFlySpeed(0.2F);
        //UHC.players.remove(player.getUniqueId());
        player.sendMessage(ChatUtils.message("&eYou are now in staff mode!"));
    }

    public boolean isVanished() {
        return vanished;
    }

    public void setVanished(boolean vanished) {
        this.vanished = vanished;
    }

    /***
     *
     * @param forceHide Should the given player be hidden from spectators as well
     */
    public void vanish(boolean forceHide){
        setVanished(true);
        if(forceHide){
            Bukkit.getOnlinePlayers().forEach(o -> o.hidePlayer(getPlayer()));
        }else{
            UHCPlayerColl.get().getAllOnline().stream().filter(uhcPlayer -> !uhcPlayer.isSpectator()).forEach(uhcPlayer -> uhcPlayer.getPlayer().hidePlayer(getPlayer()));
        }
    }

    public void unVanish(){
        setVanished(false);
        Bukkit.getOnlinePlayers().forEach(o -> o.showPlayer(getPlayer()));
    }

    public void joinArena() {
        Player p = getPlayer();
        this.setArenaSession(new ArenaSession());
        UHC.get().getScoreboardManager().getPlayerScoreboards().put(p, new PlayerScoreboard(new ArenaProvider(), p));
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        p.setAllowFlight(false);
        p.setFlying(false);
        p.setGameMode(GameMode.SURVIVAL);

        this.playerStatus = PlayerStatus.ARENA;

        ItemBuilder sword = new ItemBuilder(Material.IRON_SWORD).enchantment(Enchantment.DAMAGE_ALL, 2);
        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        ItemBuilder helmet = new ItemBuilder(Material.IRON_HELMET).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemBuilder chestplate = new ItemBuilder(Material.IRON_CHESTPLATE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemBuilder leggings = new ItemBuilder(Material.IRON_LEGGINGS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemBuilder boots = new ItemBuilder(Material.IRON_BOOTS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemBuilder bow = new ItemBuilder(Material.BOW).enchantment(Enchantment.ARROW_DAMAGE, 2).enchantment(Enchantment.ARROW_INFINITE);
        ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE, 1);
        ItemStack arrow = new ItemStack(Material.ARROW, 1);

        p.getInventory().addItem(sword.make());
        p.getInventory().addItem(rod);
        p.getInventory().addItem(bow.make());
        p.getInventory().addItem(gapple);
        p.getInventory().setItem(8, arrow);
        p.getInventory().setBoots(boots.make());
        p.getInventory().setLeggings(leggings.make());
        p.getInventory().setChestplate(chestplate.make());
        p.getInventory().setHelmet(helmet.make());
        p.setHealth(p.getMaxHealth());
        p.setFoodLevel(20);
        if (MConf.get().getArenaLocation() != null) {
            p.teleport(MConf.get().getArenaLocation().asBukkitLocation(true));
        }
    }

    public void leaveArena(){
        if(!this.isInArena()) return;
        if(!this.isPlayer()) return;
        Player p = getPlayer();
        ArenaSession session = this.arenaSession;
        session.setEnd(new Timestamp(System.currentTimeMillis()));
        updateStats(session);
        this.playerStatus = PlayerStatus.LOBBY;
        UHC.get().getScoreboardManager().getPlayerScoreboards().put(p, new PlayerScoreboard(new UHCProvider(), p));
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        p.teleport(MConf.get().getSpawnLocation().asBukkitLocation(true));
    }

    private void updateStats(ArenaSession session) {
        this.arenaKills += session.getKills();
        this.arenaDeaths += session.getDeaths();
        if(this.highestArenaKillStreak < session.getKillstreak()){
            this.highestArenaKillStreak = session.getKillstreak();
        }
        this.arenaSwordHits += session.getSwordHits();
        this.arenaSwordSwings += session.getSwordSwings();
        this.arenaGapplesEaten += session.getGapplesEaten();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("kills", session.getKills());
        jsonObject.addProperty("deaths", session.getDeaths());
        jsonObject.addProperty("highestKillStreak", session.getKillstreak());
        jsonObject.addProperty("swordHits", session.getSwordHits());
        jsonObject.addProperty("swordSwings", session.getSwordSwings());
        jsonObject.addProperty("gapplesEaten", session.getGapplesEaten());
        jsonObject.addProperty("bowAttempts", session.getBowAttempts());
        jsonObject.addProperty("bowHits", session.getBowHits());
        jsonObject.addProperty("startTime", session.getStart().getTime());
        jsonObject.addProperty("endTime", session.getEnd().getTime());
        this.pastArenaSessions.add(jsonObject.toString());
    }

    public void handleArenaKill() {
        ArenaSession session = this.arenaSession;
        session.setKills(session.getKills() + 1);
        session.setKillstreak(session.getKillstreak() + 1);
    }

    public void handleArenaDeath() {
        ArenaSession session = this.arenaSession;
        session.setDeaths(session.getDeaths() + 1);
        if (session.getKillstreak() > this.highestArenaKillStreak) {
            this.highestArenaKillStreak = session.getKillstreak();
        }
        session.setKillstreak(0);
    }

    public boolean isReceivingToggleSneakAlerts() {
        return receivingToggleSneakAlerts;
    }

    public void setReceivingToggleSneakAlerts(boolean receivingToggleSneakAlerts) {
        this.receivingToggleSneakAlerts = receivingToggleSneakAlerts;
    }

    public boolean isReceivingMiningAlerts() {
        return receivingMiningAlerts;
    }

    public void setReceivingMiningAlerts(boolean receivingMiningAlerts) {
        this.receivingMiningAlerts = receivingMiningAlerts;
    }

    public boolean isReceivingSpectatorInfo() {
        return receivingSpectatorInfo;
    }

    public void setReceivingSpectatorInfo(boolean receivingSpectatorInfo) {
        this.receivingSpectatorInfo = receivingSpectatorInfo;
    }

    public boolean isReceivingCommandSpy() {
        return receivingCommandSpy;
    }

    public void setReceivingCommandSpy(boolean receivingCommandSpy) {
        this.receivingCommandSpy = receivingCommandSpy;
    }

    public double getChangedLevel() {
        return changedLevel;
    }

    public void setChangedLevel(double changedLevel) {
        this.changedLevel = changedLevel;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerStatus(PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
    }

    public int getArenaKills() {
        return arenaKills;
    }

    public void setArenaKills(int arenaKills) {
        this.arenaKills = arenaKills;
    }

    public int getArenaDeaths() {
        return arenaDeaths;
    }

    public void setArenaDeaths(int arenaDeaths) {
        this.arenaDeaths = arenaDeaths;
    }

    public int getHighestArenaKillStreak() {
        return highestArenaKillStreak;
    }

    public void setHighestArenaKillStreak(int highestArenaKillStreak) {
        this.highestArenaKillStreak = highestArenaKillStreak;
    }

    public double getArenaKDR() {
        if (this.arenaKills == 0) {
            return 0;
        }

        if (this.arenaDeaths == 0) {
            return this.arenaKills;
        }

        return this.arenaKills / this.arenaDeaths;
    }

    public ArenaSession getArenaSession() {
        return arenaSession;
    }

    public void setArenaSession(ArenaSession arenaSession) {
        this.arenaSession = arenaSession;
    }

    public ArrayList<String> getPastArenaSessions() {
        return pastArenaSessions;
    }

    public void setPastArenaSessions(ArrayList<String> pastArenaSessions) {
        this.pastArenaSessions = pastArenaSessions;
    }

    public int getArenaSwordHits() {
        return arenaSwordHits;
    }

    public void setArenaSwordHits(int arenaSwordHits) {
        this.arenaSwordHits = arenaSwordHits;
    }

    public int getArenaSwordSwings() {
        return arenaSwordSwings;
    }

    public void setArenaSwordSwings(int arenaSwordSwings) {
        this.arenaSwordSwings = arenaSwordSwings;
    }

    public int getArenaGapplesEaten() {
        return arenaGapplesEaten;
    }

    public void setArenaGapplesEaten(int arenaGapplesEaten) {
        this.arenaGapplesEaten = arenaGapplesEaten;
    }

    public int getArenaBowAttempts() {
        return arenaBowAttempts;
    }

    public void setArenaBowAttempts(int arenaBowAttempts) {
        this.arenaBowAttempts = arenaBowAttempts;
    }

    public int getArenaBowHits() {
        return arenaBowHits;
    }

    public void setArenaBowHits(int arenaBowHits) {
        this.arenaBowHits = arenaBowHits;
    }
}
