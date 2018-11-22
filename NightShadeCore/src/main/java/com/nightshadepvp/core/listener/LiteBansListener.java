package com.nightshadepvp.core.listener;

import com.nightshadepvp.core.Core;
import litebans.api.Entry;
import litebans.api.Events;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
                    PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"Warning!\",\"color\":\"dark_red\",\"bold\":true}"), 0, 40, 0);
                    PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + entry.getReason() + "\",\"color\":\"white\"}"), 0, 40, 0);
                    ((CraftPlayer) target).getHandle().playerConnection.sendPacket(title);
                    ((CraftPlayer) target).getHandle().playerConnection.sendPacket(subtitle);
                }
            }.runTask(Core.get());
        }
    }
}
