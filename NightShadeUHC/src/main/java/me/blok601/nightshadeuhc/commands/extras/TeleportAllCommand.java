package me.blok601.nightshadeuhc.commands.extras;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.utils.ChatUtils;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * Created by Blok on 9/10/2017.
 */
public class TeleportAllCommand implements UHCCommand {
    @Override
    public String[] getNames() {
        return new String[]{
                "tpall"
        };
    }

    @Override
    public void onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;

        if(Bukkit.getOnlinePlayers().size() == 1){
            p.sendMessage(ChatUtils.message("&cThere are not enough players online to perform this action!"));
            return;
        }

        p.sendMessage(ChatUtils.message("&eTeleporting everyone to you..."));

        ArrayList<Player> online = new ArrayList<>();
//        for (Player player : Bukkit.getOnlinePlayers()){
//            online.add(player);
//        }
//        online.remove(p);

        Bukkit.getOnlinePlayers().stream().filter(o -> !o.getName().equalsIgnoreCase(p.getName())).forEach(online::add);

        new BukkitRunnable(){
            Player player;
            @Override
            public void run() {
                player = online.get(0);
                player.teleport(p);
                player.sendMessage(ChatUtils.message("&eTeleported to " + p.getName()));
                online.remove(player);

                if(online.isEmpty()){
                    cancel();
                }
            }
        }.runTaskTimer(UHC.get(), 0, 1);

    }

    @Override
    public boolean playerOnly() {
        return true;
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
