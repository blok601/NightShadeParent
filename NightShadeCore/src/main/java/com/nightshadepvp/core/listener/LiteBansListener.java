package com.nightshadepvp.core.listener;

import com.google.gson.JsonObject;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import litebans.api.Entry;
import litebans.api.Events;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;

import java.util.Objects;
import java.util.UUID;

/**
 * Created by Blok on 11/18/2018.
 */
public class LiteBansListener extends Events.Listener {

    @Override
    public void entryAdded(Entry entry) {
        if (entry.getType().equalsIgnoreCase("warn")) {
            Player target = Bukkit.getPlayer(UUID.fromString(Objects.requireNonNull(entry.getUuid())));
            if (target == null) {
                System.out.println("This was null! " + entry.getUuid());
                return;
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    target.playSound(target.getLocation(), Sound.ANVIL_BREAK, 5, 5);
                    PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"Warning!\",\"color\":\"dark_red\",\"bold\":true}"), 0, 40, 0);
                    PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + entry.getReason() + "\",\"color\":\"white\"}"), 0, 40, 0);
                    ((CraftPlayer) target).getHandle().playerConnection.sendPacket(title);
                    ((CraftPlayer) target).getHandle().playerConnection.sendPacket(subtitle);
                }
            }.runTask(Core.get());
        }

        String name;
        Player player = Bukkit.getPlayer(entry.getUuid());
        if(player == null){
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(entry.getUuid());
            if(offlinePlayer == null) return;
            name = offlinePlayer.getName();
        }else{
            name = player.getName();
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", entry.getType());
        jsonObject.addProperty("player", name);
        jsonObject.addProperty("punisher", entry.getExecutorName());
        jsonObject.addProperty("reason", entry.getReason());
        jsonObject.addProperty("length", entry.getRemainingDurationString(System.currentTimeMillis()));

        new BukkitRunnable() {
            @Override
            public void run() {
                Jedis jedis = Core.get().getJedis();
                if (jedis == null) {
                    Core.get().getLogManager().log(Logger.LogType.SEVERE, "Jedis was null!");
                    return;
                }

                jedis.publish("punishments", ChatColor.stripColor(jsonObject.toString()));
            }
        }.runTaskAsynchronously(Core.get());

    }
}
