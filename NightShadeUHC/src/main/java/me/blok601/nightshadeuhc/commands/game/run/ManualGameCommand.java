package me.blok601.nightshadeuhc.commands.game.run;


import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.GameState;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 9/10/2017.
 */
public class ManualGameCommand implements UHCCommand {
    @Override
    public String[] getNames() {
        return new String[]{
                "manualgame"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;

        if(GameState.getState() == GameState.WAITING){
            p.sendMessage(ChatUtils.message("&cThere is no game running!"));
            return;
        }

        GameManager manager = GameManager.get();

        if(manager.getFinalHealTask() != null){
            Bukkit.getScheduler().cancelTask(GameManager.get().getFinalHealTask().getTaskId());
        }

        if(manager.getPvpTask() != null){
            Bukkit.getScheduler().cancelTask(GameManager.get().getPvpTask().getTaskId());
        }

        if (manager.getMeetupTask() != null) {
            Bukkit.getScheduler().cancelTask(manager.getMeetupTask().getTaskId());
        }

        if(manager.getWorldBorderTask() != null){
            Bukkit.getScheduler().cancelTask(GameManager.get().getWorldBorderTask().getTaskId());
        }

        p.sendMessage(ChatUtils.message("&eSuccessfully canceled all game tasks! You are now in manual mode!"));
        p.sendMessage(ChatUtils.message("&eYou are now in manual mode!"));
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
