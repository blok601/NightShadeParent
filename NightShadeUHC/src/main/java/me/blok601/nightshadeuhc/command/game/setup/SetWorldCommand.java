package me.blok601.nightshadeuhc.command.game.setup;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.utils.ChatUtils;
import com.wimbli.WorldBorder.BorderData;
import com.wimbli.WorldBorder.WorldBorder;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.manager.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 9/17/2017.
 */
public class SetWorldCommand implements UHCCommand {

    private GameManager gameManager;

    public SetWorldCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public String[] getNames() {
        return new String[]{
                "setworld"
        };
    }

    @Override
    public void onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;

        gameManager.setWorld(p.getWorld());
        BorderData r = WorldBorder.plugin.getWorldBorder(p.getWorld().getName());
        if (r == null) {
            p.sendMessage(ChatUtils.message("&c The border for that world has not been setup yet! Please do this now!"));
            return;
        }
        gameManager.setSetupRadius(r.getRadiusX());

        p.sendMessage(ChatUtils.message("&eYou have set the world to &a" + gameManager.getWorld().getName()));

    }

    @Override
    public boolean playerOnly() {
        return false;
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
