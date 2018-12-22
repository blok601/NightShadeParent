package me.blok601.nightshadeuhc.manager;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.entity.object.Team;
import me.blok601.nightshadeuhc.scoreboard.PlayerScoreboard;
import me.blok601.nightshadeuhc.scoreboard.ScoreboardManager;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Blok on 9/24/2017.
 */
public class TeamManager {

    private boolean teamManagement = false;
    private int teamSize = 2;
    private boolean teamFriendlyFire = true;
    private boolean isRvB = false;
    private int RvBScatterType = 1; //1 - Teams, 0 is solo
    private boolean randomTeams = false;

    private static TeamManager ourInstance = new TeamManager();

    public static TeamManager getInstance() {
        return ourInstance;
    }

    private TeamManager() {
    }

    private ArrayList<Team> teams = new ArrayList<>();

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public void removeTeam(Team team) {
        this.teams.remove(team);
    }

    public boolean isTeam(String string) {
        return getTeam(string) != null;
    }

    public Team getTeam(String name) {
        for (Team team : this.teams) {
            if (team.getName().equalsIgnoreCase(name)) return team;
        }

        return null;
    }

    public Team getTeam(Player player) {
        for (Team team : this.teams) {
            for (String member : team.getMembers()){
                if(member.toLowerCase().equalsIgnoreCase(player.getName().toLowerCase())){
                    return team;
                }
            }
        }
        return null;
    }

    public Team getTeambyPlayerOnTeam(String name){
        for (Team team : this.teams){
            for (String member : team.getMembers()){
                if(member.toLowerCase().equalsIgnoreCase(name.toLowerCase())){
                    return team;
                }
            }
        }

        return null;
    }

    public boolean isTeamManagement() {
        return teamManagement;
    }

    public void setTeamManagement(boolean teamManagement) {
        this.teamManagement = teamManagement;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public boolean isTeamFriendlyFire() {
        return teamFriendlyFire;
    }

    public void setTeamFriendlyFire(boolean teamFriendlyFire) {
        this.teamFriendlyFire = teamFriendlyFire;
    }

    public boolean isRvB() {
        return isRvB;
    }

    public void setRvB(boolean rvB) {
        isRvB = rvB;
    }

    public int getRvBScatterType() {
        return RvBScatterType;
    }

    public void setRvBScatterType(int rvBScatterType) {
        RvBScatterType = rvBScatterType;
    }

    public boolean isRandomTeams() {
        return randomTeams;
    }

    public void setRandomTeams(boolean randomTeams) {
        this.randomTeams = randomTeams;
    }

    public void resetTeams(){
        ScoreboardManager scoreboardManager = UHC.get().getScoreboardManager();
        TeamManager.getInstance().getTeams().clear();
        Scoreboard scoreboard;

        for (Map.Entry<Player, PlayerScoreboard> playerPlayerScoreboardEntry : scoreboardManager.getPlayerScoreboards().entrySet()) {
            if (playerPlayerScoreboardEntry.getKey() == null) continue;
            if (playerPlayerScoreboardEntry.getValue() == null) continue;
            scoreboard = playerPlayerScoreboardEntry.getValue().getBukkitScoreboard();
            for (org.bukkit.scoreboard.Team team : scoreboard.getTeams()){
                if(team == null) continue;

                if(team.getName().startsWith("UHC")){
                    team.unregister();
                }

                if(team.getScoreboard().getTeam("RED") == null && team.getScoreboard().getTeam("BLUE") == null) continue;
                if(team.getName().equalsIgnoreCase("RED") || team.getName().equalsIgnoreCase("BLUE")){
                    team.unregister();
                }
            }
        }
    }
}
