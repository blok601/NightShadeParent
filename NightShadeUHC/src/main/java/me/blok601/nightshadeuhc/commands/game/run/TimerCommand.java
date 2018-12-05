package me.blok601.nightshadeuhc.commands.game.run;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import me.blok601.nightshadeuhc.tasks.TimerTask;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Blok on 10/1/2017.
 */
public class TimerCommand implements UHCCommand{
    @Override
    public String[] getNames() {
        return new String[]{
                "timer"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        if(args.length != 1){
            s.sendMessage(ChatUtils.message("&cUsage: /timer <start/stop>"));
        }else{
            if(args[0].equalsIgnoreCase("start") || args[0].equalsIgnoreCase("on")){
                if(!GameManager.get().getTimer().isRunning()){
                    TimerTask timerTask = GameManager.get().getTimer();
                    timerTask.start();
                    s.sendMessage(ChatUtils.message("&eThe timer has started!"));
                }else{
                    s.sendMessage(ChatUtils.message("&cThe timer is already running!"));
                }
            }else if(args[0].equalsIgnoreCase("stop") || args[0].equalsIgnoreCase("off")){
                if(GameManager.get().getTimer() == null){
                    s.sendMessage(ChatUtils.message("&cThe timer isn't running!"));
                    return;
                }

                Bukkit.getScheduler().cancelTask(GameManager.get().getTimer().getTaskID());
                GameManager.get().getTimer().setRunning(false);
                s.sendMessage(ChatUtils.message("&eYou have stopped the timer!"));
            }
        }


    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.HOST;
    }

    @Override
    public boolean hasRequiredRank() {
        return true;
    }
}
