package me.blok601.nightshadeuhc.staff.listener;

import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Blok on 8/4/2017.
 */
public class CommandSpyListener implements Listener{

    private ArrayList<String> commands = new ArrayList<String>(Arrays.asList("/bukkit:me", "/rl", "/reload"));

    @EventHandler(ignoreCancelled = true)
    public void cmd(PlayerCommandPreprocessEvent event){
        Player p = event.getPlayer();

        if(event.getMessage().startsWith("/cmdspy")) return;

        if(commands.contains(event.getMessage())){
            p.sendMessage(ChatUtils.message("&cYou can't use that command!"));
            return;
        }

        ChatUtils.sendCommandSpyMessage(event.getMessage(), p);
    }

}
