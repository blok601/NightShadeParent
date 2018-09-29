package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.events.GameStartEvent;
import me.blok601.nightshadeuhc.teams.Team;
import me.blok601.nightshadeuhc.teams.TeamManager;
import me.blok601.nightshadeuhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Blok on 9/28/2018.
 */
public class TeamInventoryScenario extends Scenario{

    public static HashMap<Team, Inventory> teamInventories = null;
    public static HashMap<UUID, Inventory> soloInventories = null;

    public TeamInventoryScenario() {
        super("Team Inventory", "Each team get's their own shared inventory", "TI", new ItemBuilder(Material.ENDER_CHEST).name("Team Inventory").make());
    }

    @EventHandler
    public void onStart(GameStartEvent e){
        if(teamInventories == null){
            teamInventories = new HashMap<>();
        }

        if(soloInventories == null){
            soloInventories = new HashMap<>();
        }

        if(teamInventories.size() != 0) teamInventories.clear();
        if(soloInventories.size() != 0) soloInventories.clear();

        for (Team team : TeamManager.getInstance().getTeams()){
            teamInventories.put(team, Bukkit.createInventory(null, 27, "Team Inventory"));
        }

        UHCPlayerColl.get().getAllOnline().stream()
                .filter(uhcPlayer -> !uhcPlayer.isSpectator())
                .filter(uhcPlayer -> TeamManager.getInstance().getTeam(uhcPlayer.getPlayer()) == null)
                .forEach(uhcPlayer -> soloInventories.put(uhcPlayer.getUuid(), Bukkit.createInventory(null, 27, "Team Inventory")));
    }
}
