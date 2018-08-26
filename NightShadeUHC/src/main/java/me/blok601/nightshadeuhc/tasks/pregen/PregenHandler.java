package me.blok601.nightshadeuhc.tasks.pregen;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 6/23/2018.
 */
public class PregenHandler {

    private PregenTask pregenTask = null;

    public void startPregenTask(World world, int radius, int speed, boolean force, Player sender){
        if(pregenTask != null){
            sender.sendMessage(ChatUtils.message("&cA pregen task is already running!"));
            return;
        }

        int ticks = 1;
        int repeats = 1;

        if(speed > 20){ //Taken from Leon
            repeats = speed / 20;
        }else{
            ticks = 20 /speed;
        }

        pregenTask = new PregenTask(world, radius, repeats, ticks, force, null); //No callback until later updates
        pregenTask.runTaskTimer(UHC.get(), ticks, ticks);
    }

    public void cancelPregen(Player player){
        if(pregenTask == null){
            player.sendMessage(ChatUtils.message("&cThere is no pregen running!"));
            return;
        }

        pregenTask.cancel();
        pregenTask = null;
    }


}
