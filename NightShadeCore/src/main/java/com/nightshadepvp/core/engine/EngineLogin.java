package com.nightshadepvp.core.engine;

import com.massivecraft.massivecore.Engine;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.NSPlayerColl;
import com.nightshadepvp.core.utils.ChatUtils;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Created by Blok on 6/20/2019.
 */
public class EngineLogin extends Engine {

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event){
        UUID uuid = event.getUniqueId();
        if(NSPlayer.get(uuid) == null){
            ArrayList<Consumer<UUID>> toDo = new ArrayList<>();
            toDo.add(uuid1 -> {
                Bukkit.getPlayer(uuid1).sendMessage(ChatUtils.format("&f&m&o------------------------------------------"));
                Bukkit.getPlayer(uuid1).sendMessage(ChatUtils.format("&bWelcome to NightShadePvP!"));
                Bukkit.getPlayer(uuid1).sendMessage(ChatUtils.format("&bIf you found out about NightShade from another player, type /refer <player>"));
                Bukkit.getPlayer(uuid1).sendMessage(ChatUtils.format("&bThey will receive a special reward!"));
                Bukkit.getPlayer(uuid1).sendMessage(ChatUtils.format("&f&m&o------------------------------------------"));
            });
            Core.get().getLoginTasks().put(uuid, toDo);
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player player = e.getPlayer();
        NSPlayer nsPlayer = NSPlayer.get(player);
        if (nsPlayer.hasRank(Rank.ADMIN)) {
            nsPlayer.setLoggedIn(false); //They need to login
        } else {
            nsPlayer.setLoggedIn(true);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        NSPlayer nsPlayer = NSPlayer.get(player);
        nsPlayer.setLoggedIn(false); //value will be lost on log out anyway
        nsPlayer.setLastSeen(new Date());
        nsPlayer.changed();
        e.setQuitMessage("&c&l- " + player.getName());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreProcess(PlayerCommandPreprocessEvent e){
        Player player = e.getPlayer();
        NSPlayer nsPlayer = NSPlayer.get(player);
        if(nsPlayer.isLoggedIn()) return;

        if(!nsPlayer.hasRank(Rank.ADMIN)) return;

        if(e.getMessage().startsWith("/setpassword") || e.getMessage().startsWith("/setlogin")) return; //That's allowed

        if(e.getMessage().startsWith("/login")) return; //Also allowed

        e.setCancelled(true);
        player.playSound(player.getLocation(), Sound.NOTE_BASS, 0.5F, 0.5F);
        player.sendMessage(ChatUtils.message("&cYou must log in with /login first, &b" + player.getName()));
    }

}
