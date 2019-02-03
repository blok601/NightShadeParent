package me.blok601.nightshadeuhc.scenario;

import me.blok601.nightshadeuhc.entity.object.Team;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 2/3/2019.
 */
public class DependencyScenario extends Scenario {

    private GameManager gameManager;

    public DependencyScenario(GameManager gameManager) {
        super("Dependency", "Players can only craft by right clicking a teammate, crafting tables can't be crafted or used", new ItemStack(Material.WORKBENCH));

        this.gameManager = gameManager;
    }

    @Override
    public void onToggle(boolean newState, Player p) {
        if (newState) {
            //Enabled now

            if (!gameManager.isIsTeam()) {
                this.setEnabled(false);
                p.sendMessage(ChatUtils.format(getPrefix() + "&cThis can only be enabled in a teams game!"));
            }

        }
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent e) {
        if (!isEnabled()) return;

        if (e.getRecipe().getResult().getType() == Material.WORKBENCH) {
            e.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractAtEntityEvent e) {
        if (!isEnabled()) return;
        if (!(e.getRightClicked() instanceof Player)) return;
        Player clicker = e.getPlayer();
        Player clicked = (Player) e.getRightClicked();
        Team team = TeamManager.getInstance().getTeam(clicker);
        Team team1 = TeamManager.getInstance().getTeam(clicked);

        if (team == null || team1 == null) return; //Make sure they are both on a team in the first place
        if (team.getMembers().contains(clicked.getName())) {
            clicked.openWorkbench(clicked.getLocation(), true);
        }
    }
}
