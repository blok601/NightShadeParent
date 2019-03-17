package me.blok601.nightshadeuhc.command.game.run;


import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 9/10/2017.
 */
public class ManualGameCommand implements UHCCommand {
    
    private GameManager gameManager;

    public ManualGameCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

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

        if(gameManager.getFinalHealTask() != null){
            Bukkit.getScheduler().cancelTask(gameManager.getFinalHealTask().getTaskId());
        }

        if(gameManager.getPvpTask() != null){
            Bukkit.getScheduler().cancelTask(gameManager.getPvpTask().getTaskId());
        }

        if (gameManager.getMeetupTask() != null) {
            Bukkit.getScheduler().cancelTask(gameManager.getMeetupTask().getTaskId());
        }

        if(gameManager.getWorldBorderTask() != null){
            Bukkit.getScheduler().cancelTask(gameManager.getWorldBorderTask().getTaskId());
        }
        
        if(gameManager.getShrinkTask() != null){
            Bukkit.getScheduler().cancelTask(gameManager.getShrinkTask().getTaskId());
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
