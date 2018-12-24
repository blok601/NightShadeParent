package me.blok601.nightshadeuhc.command.player.teams;


import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.object.Team;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.UUID;

/**
 * Created by Blok on 12/2/2018.
 */
public class PMOresCommand implements UHCCommand {

    private HashSet<UUID> sent = new HashSet<>();

    @Override
    public String[] getNames() {
        return new String[]{
                "pmores"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        Team team = TeamManager.getInstance().getTeam(p);
        if (team == null) {
            p.sendMessage(ChatUtils.message("&cYou are not on a team!"));
            return;
        }

        if (sent.contains(p.getUniqueId())) {
            p.sendMessage(ChatUtils.message("&cYou can only send your &eonce every minute&c!"));
            return;
        }

        int diamonds = 0;
        int gold = 0;
        int iron = 0;

        for (ItemStack stack : p.getInventory().getContents()) {
            if (stack == null || stack.getType() == Material.AIR) continue;
            if (stack.getType() == Material.DIAMOND_ORE || stack.getType() == Material.DIAMOND) {
                diamonds++;
            } else if (stack.getType() == Material.GOLD_ORE || stack.getType() == Material.GOLD_INGOT) {
                gold++;
            } else if (stack.getType() == Material.IRON_ORE || stack.getType() == Material.IRON_INGOT) {
                iron++;
            }
        }

        team.message("&6" + p.getName() + " &ehas &b" + diamonds + " diamonds&7, &6" + gold + " gold&7, &eand &f" + iron + " iron.");
        sent.add(p.getUniqueId());
        new BukkitRunnable() {
            @Override
            public void run() {
                sent.remove(p.getUniqueId());
            }
        }.runTaskLater(UHC.get(), 20 * 60);

    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return null;
    }

    @Override
    public boolean hasRequiredRank() {
        return false;
    }
}
