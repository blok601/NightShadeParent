package com.nightshadepvp.tournament.entity.objects.game;

import com.massivecraft.massivecore.store.SenderEntity;
import com.massivecraft.massivecore.util.MUtil;
import com.nightshadepvp.core.fanciful.FancyMessage;
import com.nightshadepvp.tournament.Tournament;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.enums.MatchState;
import com.nightshadepvp.tournament.entity.enums.PlayerStatus;
import com.nightshadepvp.tournament.entity.handler.GameHandler;
import com.nightshadepvp.tournament.entity.handler.InventoryManager;
import com.nightshadepvp.tournament.entity.objects.data.Arena;
import com.nightshadepvp.tournament.entity.objects.data.Kit;
import com.nightshadepvp.tournament.entity.objects.player.PlayerInv;
import com.nightshadepvp.tournament.scoreboard.ScoreboardLib;
import com.nightshadepvp.tournament.scoreboard.ScoreboardSettings;
import com.nightshadepvp.tournament.scoreboard.common.EntryBuilder;
import com.nightshadepvp.tournament.scoreboard.type.Entry;
import com.nightshadepvp.tournament.scoreboard.type.Scoreboard;
import com.nightshadepvp.tournament.scoreboard.type.ScoreboardHandler;
import com.nightshadepvp.tournament.task.LogOutTimerTask;
import com.nightshadepvp.tournament.utils.ChatUtils;
import com.nightshadepvp.tournament.utils.TimeUtils;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Created by Blok on 7/18/2018.
 */
public class SoloMatch implements iMatch {

    private int matchID;
    private TPlayer player1;
    private TPlayer player2;
    private Arena arena;
    private List<TPlayer> winners;
    private MatchState matchState;
    private Set<TPlayer> spectators;
    private HashSet<Location> blocks;
    private long startTime;

    private String timer;
    private HashMap<UUID, Scoreboard> scoreboards;
    private HashMap<UUID, LogOutTimerTask> logOutTimers;


    public SoloMatch(TPlayer player1, TPlayer player2) {
        this.player1 = player1;
        this.winners = new ArrayList<>();
        this.spectators = new HashSet<>();
        this.player2 = player2;
        this.timer = "&eWaiting...";
        this.scoreboards = new HashMap<>();
        this.spectators = new HashSet<>();
        this.logOutTimers = new HashMap<>();
        this.blocks = new HashSet<>();
        freezePlayers();
    }

    public int getMatchID() {
        return matchID;
    }

    /**
     * @return Team 1 Players
     */
    @Override
    public List<TPlayer> getTeam1() {
        return Collections.singletonList(getPlayer1());
    }

    /**
     * @return Team 2 Players
     */
    @Override
    public List<TPlayer> getTeam2() {
        return Collections.singletonList(getPlayer2());
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public TPlayer getPlayer1() {
        return player1;
    }

    public void setPlayer1(TPlayer player1) {
        this.player1 = player1;
    }

    public TPlayer getPlayer2() {
        return player2;
    }

    public void setPlayer2(TPlayer player2) {
        this.player2 = player2;
    }

    public Arena getArena() {
        return arena;
    }

    /**
     * @return Winners
     */
    @Override
    public List<TPlayer> getWinners() {
        return winners;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public List<TPlayer> getPlayers() {
        return MUtil.list(player1, player2);
    }

    private void freezePlayers() {
        getPlayers().forEach(tPlayer -> tPlayer.setFrozen(true));
    }

    private void unfreezePlayers() {
        getPlayers().forEach(tPlayer -> tPlayer.setFrozen(false));
    }

    public MatchState getMatchState() {
        return matchState;
    }

    public void setMatchState(MatchState matchState) {
        this.matchState = matchState;
    }

    public String getTimer() {
        return TimeUtils.formatElapsingNanoseconds(startTime);
    }

    /**
     * @return Player scoreboards
     */
    @Override
    public HashMap<UUID, Scoreboard> getScoreboards() {
        return scoreboards;
    }

    /**
     * @param winners players who won
     */
    @Override
    public void endMatch(List<TPlayer> winners, EntityEvent event) { //TODO: Champ game check
        if (winners.size() != 1) return;

        TPlayer winner = winners.get(0);
        setWinner(winner);
        TPlayer loser = getOpponents(winner).get(0);
        Player loserPlayer = loser.getPlayer();
        loser.setSeed(-1);

        setMatchState(MatchState.DONE);
        setWinners(Collections.singletonList(winner));

        InventoryManager.getInstance().addInventory(winner.getPlayer());
        if(loser.isOnline()){
            InventoryManager.getInstance().addInventory(loserPlayer);
        }


        FancyMessage msg;

        broadcastAllFormat("&3&m-----------------");

        if(winner.isOnline()){
            msg = new FancyMessage("Winner").color(ChatColor.DARK_PURPLE).then(": ").color(ChatColor.DARK_GRAY).then(winner.getName()).color(ChatColor.GOLD).command("/viewplayerinventory " + winner.getName());
            broadcastAllFancy(msg);
        }

        if(loser.isOnline()){
            msg = new FancyMessage("Loser").color(ChatColor.DARK_PURPLE).then(": ").color(ChatColor.DARK_GRAY).then(loser.getName()).color(ChatColor.GOLD).command("/viewplayerinventory " + getOpponents(winner).get(0).getName());
            broadcastAllFancy(msg);
        }

        broadcastAllFormat("&3&m-----------------");
        broadcastAllFormat("&5Kit&8: &6" + GameHandler.getInstance().getKit().getName());

        broadcastAllFormat("&5Duration&8: &6" + getTimer());

        if (event != null & event instanceof EntityDamageByEntityEvent) { //Player kill

            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;

            e.setDamage(0);
            e.setCancelled(true);
            loserPlayer.getWorld().strikeLightningEffect(loserPlayer.getLocation());

            addSpectator(loser);
            loserPlayer.setAllowFlight(true);
            loserPlayer.setFlying(true);
            loserPlayer.setFlySpeed(0.2F);
            loserPlayer.setHealth(loserPlayer.getMaxHealth());
            loserPlayer.setCanPickupItems(false);
            winner.getPlayer().hidePlayer(loserPlayer);
        } else {
            //Died to PvE or plugin, or anything else in general

            if(event instanceof PlayerDeathEvent){
                PlayerDeathEvent playerDeathEvent = (PlayerDeathEvent) event;
                playerDeathEvent.getDrops().clear();
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                   if(loserPlayer.isOnline()){
                       loserPlayer.spigot().respawn();
                       loserPlayer.teleport(winner.getPlayer().getLocation());

                       loserPlayer.getWorld().strikeLightningEffect(loserPlayer.getLocation());
                       addSpectator(loser);
                       loserPlayer.setAllowFlight(true);
                       loserPlayer.setFlying(true);
                       loserPlayer.setFlySpeed(0.2F);
                       loserPlayer.setHealth(loserPlayer.getMaxHealth());
                       loserPlayer.setCanPickupItems(false);
                       winner.getPlayer().hidePlayer(loserPlayer);
                   }
                }
            }.runTaskLater(Tournament.get(), 2L);
        }

        //Doesn't matter how they died but do this stuff
        winner.setFightsWon(winner.getFightsWon() + 1);
        loser.setFightsLost(loser.getFightsLost() + 1);
        getScoreboards().values().forEach(Scoreboard::deactivate); //Turn all boards off
        scoreboards.clear();

        loserPlayer.sendMessage(ChatUtils.message("&eYou have died! Thank you for playing on NightShadePvP!"));
        loserPlayer.sendMessage(ChatUtils.message("&eJoin the Discord at discord.me/NightShadePvP for updates and more!"));

        new BukkitRunnable() {
            int counter = 5;

            @Override
            public void run() {
                if (counter == 2) {
                    resetBlocks();
                    getArena().setInUse(false);
                    //Teleport everyone out - completely finished
                    for (TPlayer tPlayer : getPlayers()) {
                        if (tPlayer.isOnline()) {
                            tPlayer.sendSpawn();
                        }
                    }
                }

                if (counter == 0) {
                    counter = -10;
                    cancel();
                    return;
                }

                counter--;
            }
        }.runTaskTimer(Tournament.get(), 0, 20);

        this.spectators.forEach(tPlayer -> tPlayer.getPlayer().teleport(Tournament.get().getSpawnLocation()));
        this.spectators.clear();

        if (GameHandler.getInstance().getChampionship() != null) {
            if (GameHandler.getInstance().getChampionship().getMatchID() == getMatchID()) {
                //This is the champ game

                if (winner.isOnline()) {
                    //Solo match
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            Firework fw = (Firework) winner.getPlayer().getWorld().spawnEntity(winner.getPlayer().getLocation(), EntityType.FIREWORK);
                            FireworkMeta fireworkMeta = fw.getFireworkMeta();
                            fireworkMeta.addEffect(FireworkEffect.builder().flicker(true).with(FireworkEffect.Type.BURST).withColor(Color.PURPLE, Color.BLUE, Color.ORANGE).withFade(Color.YELLOW).trail(true).build());
                            fireworkMeta.setPower(2);
                            fw.setFireworkMeta(fireworkMeta);
                            fw.detonate();
                        }
                    }.runTaskLater(Tournament.get(), 60L);
                }

                Bukkit.broadcastMessage(ChatUtils.message("&3" + winner.getName() + " &ehas won a NightShadePvP Tournament! Congratulations"));
                winner.setTournamentsWon(winner.getTournamentsWon() + 1);
                winner.setTournamentsPlayed(winner.getTournamentsPlayed() + 1); //Not incremented since they didn't die
            }
        }

    }

    public void setTimer(String timer) {
        this.timer = timer;
    }


    public void setWinner(TPlayer winner) {
        getWinners().add(winner);
    }

    public void setWinners(List<TPlayer> winners) {
        this.winners = winners;
    }

    @Override
    public List<TPlayer> getOpponents(TPlayer tPlayer) {
        if (tPlayer.getName().equalsIgnoreCase(player1.getName())) {
            return Collections.singletonList(player2);
        }

        return Collections.singletonList(player1);
    }


    private void setupBoard() {
        Scoreboard scoreboard;
        for (TPlayer tPlayer : getPlayers()) {
            if(!tPlayer.isOnline()) {

            }
            scoreboard = ScoreboardLib.createScoreboard(tPlayer.getPlayer()).setHandler(new ScoreboardHandler() {

                /**
                 * Determines the title to display for this player. If null returned, title automatically becomes a blank line.
                 *
                 * @param player player
                 * @return title
                 */
                @Override
                public String getTitle(Player player) {
                    return "&3NightShadePvP";
                }

                /**
                 * Determines the entries to display for this player. If null returned, the entries are not updated.
                 *
                 * @param player player
                 * @return entries
                 */
                @Override
                public List<Entry> getEntries(Player player) {
                    return new EntryBuilder()
                            .next((getOpponents(TPlayer.get(player)).get(0) != null && TPlayer.get(player).getName().length() >= 10 ? ScoreboardSettings.SCOREBOARD_SPACER_LARGE : ScoreboardSettings.SPACER) + ScoreboardSettings.SPACER + ScoreboardSettings.SPACER)
                            .next("&6Duration: &e" + getTimer())
                            .blank()
                            .next("&6Opponent: &e" + getOpponents(tPlayer).get(0).getName())
                            .blank()
                            .next("&6Kit: &e" + GameHandler.getInstance().getKit().getName())
                            .blank()
                            .next("&6Spectators: &e" + getSpectators().size())
                            .next((getOpponents(TPlayer.get(player)).get(0) != null && TPlayer.get(player).getName().length() >= 10 ? ScoreboardSettings.SCOREBOARD_SPACER_LARGE : ScoreboardSettings.SPACER) + ScoreboardSettings.SPACER + ScoreboardSettings.SPACER)
                            .build();
                }
            }).setUpdateInterval(4L);
            scoreboards.put(tPlayer.getUuid(), scoreboard);
            scoreboard.activate();
            tPlayer.setScoreboard(scoreboard);
        }
    }


    public void start() {
        Kit kit = GameHandler.getInstance().getKit();
        setMatchState(MatchState.STARTING);
        setupBoard();
        getPlayers().forEach(tPlayer -> {
            if (tPlayer.isOnline()) {
                PlayerInv inv = tPlayer.getInv(kit);
                tPlayer.getPlayer().getInventory().setArmorContents(inv.getArmorContents());
                tPlayer.getPlayer().getInventory().setContents(inv.getContents());
                tPlayer.getPlayer().sendMessage(ChatUtils.message("&eYou are fighting &3" + getOpponents(tPlayer).get(0).getName() + "&e using kit &3" + GameHandler.getInstance().getKit().getName()));
                tPlayer.getPlayer().setCanPickupItems(true);
                tPlayer.setStatus(PlayerStatus.PLAYING);
            }
        });
        new BukkitRunnable() {
            int counter = 5;

            @Override
            public void run() {
                if (counter == 0) {
                    counter = -10;
                    cancel();
                    startTime = System.nanoTime();
                    PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"Go!\",\"color\":\"dark_aqua\",\"bold\":true}"), 0, 10, 0);
                    for (TPlayer tPlayer : getPlayers()) {
                        if (tPlayer.isOnline()) {
                            if (tPlayer.isUsingOldVersion()) {
                                tPlayer.msg(ChatUtils.message("&3Go!"));
                                continue;
                            }
                            ((CraftPlayer) tPlayer.getPlayer()).getHandle().playerConnection.sendPacket(packet);
                        }
                    }
                    unfreezePlayers();
                    setMatchState(MatchState.INGAME);

                } else {
                    PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + counter + "\",\"color\":\"dark_aqua\",\"bold\":true}"), 0, 10, 0);
                    for (TPlayer tPlayer : getPlayers()) {
                        if (tPlayer.isOnline()) {
                            if (tPlayer.isUsingOldVersion()) {
                                tPlayer.msg("&3The game will start in &e" + counter);
                                continue;
                            }
                            ((CraftPlayer) tPlayer.getPlayer()).getHandle().playerConnection.sendPacket(packet);

                        }
                    }
                }

                counter--;
            }
        }.runTaskTimer(Tournament.get(), 0, 20);
    }

    @Override
    public void broadcast(String message) {
        getPlayers().stream().filter(SenderEntity::isOnline).forEach(tPlayer -> tPlayer.msg(ChatUtils.message(message)));
    }

    /**
     * Add a spectator to the list of spectators
     *
     * @param tPlayer TPlayer to add to spectators
     */
    @Override
    public void addSpectator(TPlayer tPlayer) {
        this.spectators.add(tPlayer);
        tPlayer.getPlayer().teleport(getTeleportableLocation());
    }

    /**
     * @return Set of spectators
     */
    @Override
    public Set<TPlayer> getSpectators() {
        return this.spectators;
    }

    @Override
    public Location getTeleportableLocation() {
        if (getPlayer1().isOnline()) {
            return getPlayer1().getPlayer().getLocation();
        }

        if (getPlayer2().isOnline())
            return getPlayer2().getPlayer().getLocation();

        return getArena().getSpawnLocation1();
    }

    @Override
    public void broadcastAll(String message) {
        broadcast(message);
        getSpectators().forEach(tPlayer -> tPlayer.msg(ChatUtils.message(message)));
    }

    @Override
    public void broadcastFancy(FancyMessage fancyMessage) {
        getPlayers().stream().filter(SenderEntity::isOnline).forEach(tPlayer -> fancyMessage.send(tPlayer.getPlayer()));
    }

    @Override
    public void broadcastAllFancy(FancyMessage fancyMessage) {
        broadcastFancy(fancyMessage);
        getSpectators().stream().filter(SenderEntity::isOnline).forEach(tPlayer -> fancyMessage.send(tPlayer.getPlayer()));
    }

    /**
     * Broadcast a formatted message to players
     */
    @Override
    public void broadcastFormat(String message) {
        getPlayers().stream().filter(SenderEntity::isOnline).forEach(tPlayer -> tPlayer.msg(ChatUtils.format(message)));
    }

    /**
     * Broadcast a formatted to all players and specs
     */
    @Override
    public void broadcastAllFormat(String message) {
        getPlayers().stream().filter(SenderEntity::isOnline).forEach(tPlayer -> tPlayer.msg(ChatUtils.format(message)));
        getSpectators().stream().filter(SenderEntity::isOnline).forEach(tPlayer -> tPlayer.msg(ChatUtils.format(message)));
    }

    /**
     * Start the logout timer for a player
     *
     * @param tPlayer player to start the logout timer for
     */
    @Override
    public void startLogOutTimer(TPlayer tPlayer) {
        LogOutTimerTask logOutTimerTask = new LogOutTimerTask(tPlayer, this);
        logOutTimerTask.runTaskTimer(Tournament.get(), 0, 20);
        this.logOutTimers.put(tPlayer.getUuid(), logOutTimerTask);
    }

    /**
     * Get the logout timer for a player
     *
     * @param tPlayer player to get the logout timer for
     * @return
     */
    @Override
    public LogOutTimerTask getLogOutTimer(TPlayer tPlayer) {
        return this.logOutTimers.getOrDefault(tPlayer.getUuid(), null);
    }

    @Override
    public void stopLogOutTimer(TPlayer tPlayer){
        if(this.logOutTimers.containsKey(tPlayer.getUuid())){
            LogOutTimerTask logOutTimerTask = this.logOutTimers.get(tPlayer.getUuid());
            logOutTimerTask.cancel();
            this.logOutTimers.remove(tPlayer.getUuid());
        }
    }

    @Override
    public HashSet<Location> getBlocks() {
        return blocks;
    }

    /**
     * Reset the placed blocks
     */
    @Override
    public void resetBlocks() {
        this.blocks.forEach(location -> location.getWorld().getBlockAt(location).setType(Material.AIR)); //Clear all the blocks
    }

    @Override
    public long getStartTimeMillis() {
        return this.startTime;
    }
}
