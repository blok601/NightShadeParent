package me.blok601.nightshadeuhc.command.staff;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 11/19/2018.
 */
public class ToggleMobsCommand implements UHCCommand {
    @Override
    public String[] getNames() {
        return new String[]{
                "togglemobs"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if (GameManager.get().getWorld() == null) {
            p.sendMessage(ChatUtils.message("&cThe world has not been set yet!"));
            return;
        }

        World world = GameManager.get().getWorld();
        //butcher mobs first
        int removed = 0;
        for (Chunk chunk : world.getLoadedChunks()) {
            for (Entity e : chunk.getEntities()) {
                if (e instanceof Monster || e instanceof Animals) {
                    e.remove();
                    removed++;
                }
            }
        }

        world.setGameRuleValue("doMobSpawning", "false");
        p.sendMessage(ChatUtils.message("&b" + removed + " &emobs have been removed. And mobs can no longer spawn"));
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.TRIAL;
    }

    @Override
    public boolean hasRequiredRank() {
        return true;
    }
}
