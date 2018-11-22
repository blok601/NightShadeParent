package me.blok601.nightshadeuhc.commands.extras;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.GameState;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 9/3/2018.
 */
public class PermaDayCommand implements CmdInterface {
    @Override
    public String[] getNames() {
        return new String[]{
                "permaday"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if(!GameState.gameHasStarted()){
            p.sendMessage(ChatUtils.message("&cThe game hasn't started yet!"));
            return;
        }

        if(GameManager.getWorld() == null){
            p.sendMessage(ChatUtils.message("&cThe world has not been set yet!"));
            return;
        }

        World world = GameManager.getWorld();
        world.setTime(100);
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("doWeatherCycle", "false");
        p.sendMessage(ChatUtils.message("&ePermaday has been enabled!"));
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
