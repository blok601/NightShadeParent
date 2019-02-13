package me.blok601.nightshadeuhc.entity.object;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.scoreboard.PlayerScoreboard;
import me.blok601.nightshadeuhc.scoreboard.ScoreboardManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Blok on 9/24/2017.
 */
public class Team {

    private String name;
    private ArrayList<String> members;
    private String prefix;

    private UUID mole;

    private UUID melee;
    private UUID bow;

    public Team(String name, Player player) {
        this.name = name;
        this.members = new ArrayList<>();
        if(player != null){
            this.members.add(player.getName());
        }

    }

    public Team(String name){
        this.name = name;
        this.members = new ArrayList<>();
    }

    public Team(String name, ArrayList<String> members){
        this.name = name;
        this.members = members;
    }


    public String getName() {
        return name;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeader() {
        return this.members.get(0);
    }

    public void message(String msg){
        Player player;
        for (String string : this.members){
            player = Bukkit.getPlayer(string);
            if(player == null) continue;

            player.sendMessage(ChatUtils.format("&3Team&8 » &e" + msg));
        }
    }

    public void removeMember(Player player){
        if(this.members.contains(player.getName())) this.getMembers().remove(player.getName());
    }

    public void addMember(Player player){
        this.members.add(player.getName());
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public UUID getMole() {
        return mole;
    }

    public void setMole(UUID mole) {
        this.mole = mole;
    }

    public UUID getMelee() {
        return melee;
    }

    public void setMelee(UUID melee) {
        this.melee = melee;
    }

    public UUID getBow() {
        return bow;
    }

    public void setBow(UUID bow) {
        this.bow = bow;
    }

    public void color() {
        String color = ChatUtils.generateTeamColor();
        ScoreboardManager scoreboardManager = UHC.get().getScoreboardManager();
        Scoreboard scoreboard;
        for (Map.Entry<Player, PlayerScoreboard> playerPlayerScoreboardEntry : scoreboardManager.getPlayerScoreboards().entrySet()) {
            if (playerPlayerScoreboardEntry.getValue() == null) continue;
            if (playerPlayerScoreboardEntry.getKey() == null) continue;

            scoreboard = playerPlayerScoreboardEntry.getValue().getBukkitScoreboard();
            if (scoreboard.getTeam(this.getName()) != null) {
                scoreboard.getTeam(this.getName()).unregister();
            }

            org.bukkit.scoreboard.Team t = scoreboard.registerNewTeam(this.getName());
            t.setPrefix(ChatUtils.format(color));
//                                if(t.getPrefix().contains("&k") || t.getPrefix().endsWith("&r")){
//                                    t.setPrefix(generateColor());
//                                }

            for (String mem : getMembers()) {
                CachedColor cachedColor = new CachedColor(this.getName());
                cachedColor.setColor(color);
                t.addEntry(mem);
                cachedColor.setPlayer(mem);
                TeamManager.getInstance().getCachedColors().add(cachedColor);
            }
        }
    }

    public void scheduleRemoval(String name) {
        for (String n : this.getMembers()) {
            if (n.equalsIgnoreCase(name)) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        getMembers().remove(n);
                    }
                }.runTaskLater(UHC.get(), 7);
            }
        }
    }

}
