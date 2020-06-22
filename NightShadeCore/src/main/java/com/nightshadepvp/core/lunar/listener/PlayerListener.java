package com.nightshadepvp.core.lunar.listener;

import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.lunar.api.event.impl.AuthenticateEvent;
import com.nightshadepvp.core.lunar.api.user.User;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Sound;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

public class PlayerListener implements Listener {

    private Core plugin;

    public PlayerListener(Core plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        User data = new User(event.getPlayer().getUniqueId(), event.getPlayer().getName());
        Core.get().getApi().getUserManager().setPlayerData(event.getPlayer().getUniqueId(), data);
    }

    @EventHandler
    public void onPlayerAuthenticate(AuthenticateEvent event) {
        Player player = event.getPlayer();
        Configuration config = plugin.getConfig();

        // Authenticate chat message
        player.sendMessage(ChatUtils.message("&bLunar Client &fhas been detected!"));
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 5, 5);

        // Server name
        if (config.getBoolean("server-name.enabled")) {
            try {
                Core.get().getApi().updateServerName(player, "NightShadePvP");
            } catch (IOException e) {
                // handle error
            }
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Core.get().getApi().getUserManager().removePlayerData(event.getPlayer().getUniqueId());
    }
}