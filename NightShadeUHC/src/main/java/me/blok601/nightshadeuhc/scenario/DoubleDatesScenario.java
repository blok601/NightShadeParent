package me.blok601.nightshadeuhc.scenario;

import com.google.common.base.Joiner;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.entity.object.Team;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Blok on 12/22/2018.
 */
public class DoubleDatesScenario extends Scenario {

    public DoubleDatesScenario() {
        super("Double Dates", "To2's are randomly combined to make To4's", new ItemBuilder(Material.DEAD_BUSH).name("Double Dates").make());
    }


    @Override
    public void onToggle(boolean newState) {
        if (newState) {
            if (GameState.gameHasStarted()) {
                //Assign them now
                assignDoubleDates();
                Bukkit.broadcastMessage(ChatUtils.format(getPrefix() + "&eDouble Dates have been assigned! Do /team list to check your new teams!"));
            }
        }
    }

    public static void assignDoubleDates() {
        List<List<Team>> listOfTeams = splitList(TeamManager.getInstance().getTeams(), 2);
        TeamManager.getInstance().resetTeams();
        String namePrefix = "UHC";
        int i = 1;
        for (List<Team> teamList : listOfTeams) {
            //List of teams to combine
            Team newTeam = new Team(namePrefix + i, ChatUtils.generateTeamColor());
            for (Team team : teamList) {
                for (String name : team.getMembers()) {
                    newTeam.getMembers().add(name);
                }
            }
            TeamManager.getInstance().addTeam(newTeam);
            i++;
            newTeam.message("&eYour new team is&8: &b" + Joiner.on("&8, &b").join(newTeam.getMembers()));
        }
    }

    private static <T> List<List<T>> splitList(List<T> list, int size) {
        Iterator<T> iterator = list.iterator();
        List<List<T>> returnList = new ArrayList<>();
        while (iterator.hasNext()) {
            List<T> tempList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                if (!iterator.hasNext()) break;
                tempList.add(iterator.next());
            }
            returnList.add(tempList);
        }
        return returnList;
    }
}
