package com.nightshadepvp.core.lunar.listener;

import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.lunar.api.user.User;
import com.nightshadepvp.core.utils.ReflectionUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ClientListener implements Listener {

    @EventHandler
    public void onRegisterChannel(PlayerRegisterChannelEvent event){
        if (event.getChannel().equals("Lunar-Client")) {
            try {
                Core.get().getApi().performEmote(event.getPlayer(), 5, false);
                Core.get().getApi().performEmote(event.getPlayer(), -1, false);

                Object nmsPlayer = event.getPlayer().getClass().getMethod("getHandle").invoke(event.getPlayer());
                Object packet = ReflectionUtils.getNmsClass("PacketPlayOutEntityStatus")
                        .getConstructor(ReflectionUtils.getNmsClass("Entity"), byte.class)
                        .newInstance(nmsPlayer, (byte)2);
                ReflectionUtils.sendPacket(event.getPlayer(), packet);
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | NoSuchFieldException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void onUnregisterChannel(PlayerUnregisterChannelEvent event) {
        User user = Core.get().getApi().getUserManager().getPlayerData(event.getPlayer());
        if (event.getChannel().equals("Lunar-Client")) {
            user.setLunarClient(false);
        }
    }

}