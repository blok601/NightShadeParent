package com.nightshadepvp.tournament.entity.handler;

import at.stefangeyer.challonge.Challonge;
import at.stefangeyer.challonge.model.Credentials;
import at.stefangeyer.challonge.rest.retrofit.RetrofitRestClient;
import at.stefangeyer.challonge.serializer.gson.GsonSerializer;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import com.nightshadepvp.tournament.Tournament;
import com.nightshadepvp.tournament.entity.TPlayer;
import com.nightshadepvp.tournament.entity.TPlayerColl;
import com.nightshadepvp.tournament.entity.objects.data.Kit;
import com.nightshadepvp.tournament.entity.objects.game.SoloMatch;
import com.nightshadepvp.tournament.entity.objects.game.iMatch;
import com.nightshadepvp.tournament.task.StartRoundTask;
import com.nightshadepvp.tournament.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Blok on 6/13/2018.
 */
public class GameHandler {

    private static GameHandler ourInstance = new GameHandler();

    public static GameHandler getInstance() {
        return ourInstance;
    }

    private Kit kit;
    private boolean teams;
    private Player host;
    private int slots;
    private int teamSize;
    private boolean chatFrozen;

    private Challonge challonge;
    private String bracketLink;

    private HashSet<String> whitelist;
    private boolean whitelistOn;

    private boolean seeded;
    private StartRoundTask startRoundTask;

    private iMatch championship;

    private HashMap<UUID, Location> teleportQueue;


    public void setup() {
        this.kit = null;
        this.teams = false;
        this.host = null;
        this.teamSize = 1;
        this.seeded = false;
        this.startRoundTask = null;
        this.teleportQueue = new HashMap<>();
        this.chatFrozen = false;
        this.whitelist = new HashSet<>();
        this.whitelistOn = false;

        this.challonge = new Challonge(new Credentials("NightShadePvP", "PC4Qd6rMkNxBn4v7KKLTXJRGyDwlgOOdd6gnArUp"), new GsonSerializer(), new RetrofitRestClient());
        this.bracketLink = "https://challonge.com/users/nightshadepvp/tournaments";

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        if(scoreboard.getObjective("health") != null) scoreboard.getObjective("health").unregister();
        Objective health = scoreboard.registerNewObjective("health", "health");
        health.setDisplayName(ChatColor.RED + "❤");
        health.setDisplaySlot(DisplaySlot.BELOW_NAME);
    }

    public Kit getKit() {
        return kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public boolean isTeams() {
        return teams;
    }

    public void setTeams(boolean teams) {
        this.teams = teams;
    }

    public Player getHost() {
        return host;
    }

    public void setHost(Player host) {
        this.host = host;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public void assignSeeds(){
        HashMap<UUID, Double> wins = new HashMap<>();
        TPlayerColl.get().getAllOnline().stream().filter(tPlayer -> !tPlayer.isSpectator())
                .forEach(tPlayer -> wins.put(tPlayer.getUuid(), tPlayer.getWinPCT()));

        LinkedHashMap<UUID, Double> f = wins.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue, (aDouble, aDouble2) -> aDouble, LinkedHashMap::new));

        int i = 1;
        TPlayer tPlayer;
        for (Map.Entry<UUID, Double> entry : f.entrySet()){
            tPlayer = TPlayer.get(entry.getKey());
            tPlayer.setSeed(i);
            tPlayer.msg(ChatUtils.message("&eYou are seeded: &3" + i));
            i++;
        }
        seeded = true;
    }


    public void assignMatches() {
        if (getTeamSize() == 1) {
            ArrayDeque<TPlayer> players = new ArrayDeque<>();
            TPlayerColl.get().getAllOnline().stream().filter(TPlayer::isSeeded).forEach(players::add);
            Core.get().getLogManager().log(Logger.LogType.DEBUG, "Player Amount: " + players.size());
            RoundHandler.getInstance().populateRound(RoundHandler.getInstance().getRound());

            new BukkitRunnable() {
                TPlayer tPlayer1;
                TPlayer tPlayer2;

                @Override
                public void run() {

                    if (players.size() == 0) {
                        cancel();
                        //Call start tasks
                        new StartRoundTask().runTaskTimer(Tournament.get(), 0, 20);
                        Core.get().getLogManager().log(Logger.LogType.DEBUG, "Amount of matches in this list: " +RoundHandler.getInstance().getMatchesByRoundNumber(RoundHandler.getInstance().getRound()).size());
                        if(RoundHandler.getInstance().getMatchesByRoundNumber(RoundHandler.getInstance().getRound()).size() == 1){
                            //Its the champ game
                            setChampionship(RoundHandler.getInstance().getMatchesByRoundNumber(RoundHandler.getInstance().getRound()).stream().findFirst().get());
                        }
                        return;
                    }

                    tPlayer1 = players.getFirst();
                    tPlayer2 = players.getLast();
                    SoloMatch soloMatch = new SoloMatch(tPlayer1, tPlayer2);
                    Core.get().getLogManager().log(Logger.LogType.DEBUG, "Players: " + tPlayer1.getName() + " And " + tPlayer2.getName());
                    RoundHandler.getInstance().addMatch(RoundHandler.getInstance().getRound(), soloMatch);
                    MatchHandler.getInstance().addMatch(soloMatch);
                    players.remove(tPlayer1);
                    players.remove(tPlayer2);
                }
            }.runTaskTimer(Tournament.get(), 0, 2);
        }
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public boolean isSeeded() {
        return seeded;
    }

    public void setSeeded(boolean seeded) {
        this.seeded = seeded;
    }

    public StartRoundTask getStartRoundTask() {
        return startRoundTask;
    }

    public void setStartRoundTask(StartRoundTask startRoundTask) {
        this.startRoundTask = startRoundTask;
    }

    public iMatch getChampionship() {
        return championship;
    }

    public void setChampionship(iMatch championship) {
        this.championship = championship;
    }

    public HashMap<UUID, Location> getTeleportQueue() {
        return teleportQueue;
    }

    public boolean isChatFrozen() {
        return chatFrozen;
    }

    public void setChatFrozen(boolean chatFrozen) {
        this.chatFrozen = chatFrozen;
    }

    public HashSet<String> getWhitelist() {
        return whitelist;
    }

    public void setWhitelist(HashSet<String> whitelist) {
        this.whitelist = whitelist;
    }

    public boolean isWhitelistOn() {
        return whitelistOn;
    }

    public void setWhitelistOn(boolean whitelistOn) {
        this.whitelistOn = whitelistOn;
    }

    public Challonge getChallonge() {
        return challonge;
    }

    public String getBracketLink() {
        return bracketLink;
    }

    public void setBracketLink(String bracketLink) {
        this.bracketLink = bracketLink;
    }
}
