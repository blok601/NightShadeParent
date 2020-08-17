package com.nightshadepvp.core.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import net.md_5.bungee.api.chat.BaseComponent;
import net.minecraft.server.v1_8_R3.*;
import org.apache.commons.lang.Validate;
import org.bukkit.*;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

/**
 * Created by Blok on 3/5/2019.
 */
public class PacketUtils {

    private static final ProtocolManager PROTOCOL_MANAGER = ProtocolLibrary.getProtocolManager();

    public static EntityPlayer getHandle(Player player) {
        if (!(player instanceof CraftPlayer))
            Core.get().getLogManager().log(Logger.LogType.SEVERE, "CraftBukkit not found!");
        return ((CraftPlayer) player).getHandle();
    }

    public static EntityLiving getHandle(LivingEntity player) {
        if (!(player instanceof CraftLivingEntity))
            Core.get().getLogManager().log(Logger.LogType.SEVERE, "CraftBukkit not found!");
        return ((CraftLivingEntity) player).getHandle();
    }

    public static MinecraftServer getServer() {
        Server server = Bukkit.getServer();
        if (!(server instanceof CraftServer))
            Core.get().getLogManager().log(Logger.LogType.SEVERE, "CraftBukkit not found!");
        return ((CraftServer) server).getServer();
    }

    public static WorldServer getHandle(World world) {
        if (!(world instanceof CraftWorld))
            Core.get().getLogManager().log(Logger.LogType.SEVERE, "CraftBukkit not found!");
        return ((CraftWorld) world).getHandle();
    }

    public static void sendPacket(Player target, Packet packet) {
        getHandle(target).playerConnection.sendPacket(packet);
    }

    public static void sendPacket(Player target, PacketContainer packet) {
        try {
            PROTOCOL_MANAGER.sendServerPacket(target, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void updateScoreboardLine(Player player, String entry, int bukkitValue) {
        PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.SCOREBOARD_SCORE);

        packetContainer.getScoreboardActions().write(0, EnumWrappers.ScoreboardAction.CHANGE);
        packetContainer.getIntegers().write(0, bukkitValue);
        packetContainer.getStrings().write(0, entry);
        packetContainer.getStrings().write(1, "sidebar"); //Display slot
        sendPacket(player, packetContainer);
    }

    public static String getNameFromUUID(UUID uuid) {
        Validate.notNull(uuid, "UUID can't be null!");
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            return player.getName();
        }

        //Player is offline
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if (offlinePlayer == null) {
            return "";
        }

        return offlinePlayer.getName();
    }

    @Deprecated
    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message)
    {
        sendTitle(player, fadeIn, stay, fadeOut, message, null);
    }

    @Deprecated
    public static void sendSubtitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message) {
        sendTitle(player, fadeIn, stay, fadeOut, null, message);
    }

    @Deprecated
    public static void sendFullTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
    }

    public static void sendTitle(Player player, int fadeIn, int stay, int fadeOut, String title, String subtitle) {
        //net.minecraft.server.v1_8_R3.PlayerConnection connection = ((org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer) player).getHandle().playerConnection;

        net.minecraft.server.v1_8_R3.PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, new BaseComponent[]{}, fadeIn, stay, fadeOut);
        sendPacket(player, packetPlayOutTimes);

        if (subtitle != null) {
            subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
            subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
            net.minecraft.server.v1_8_R3.IChatBaseComponent titleSub = net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
            net.minecraft.server.v1_8_R3.PacketPlayOutTitle packetPlayOutSubTitle = new net.minecraft.server.v1_8_R3.PacketPlayOutTitle(net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleSub);
            sendPacket(player, packetPlayOutSubTitle);
        }

        if (title != null) {
            title = title.replaceAll("%player%", player.getDisplayName());
            title = ChatUtils.format(title);
            IChatBaseComponent titleMain = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
            PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleMain);
            sendPacket(player, packetPlayOutTitle);
        }
    }

    public static void sendTabTitle(Player player, String header, String footer) {
        if (header == null) header = "";
        header = ChatUtils.format(header);

        if (footer == null) footer = "";
        footer = ChatUtils.format(footer);

        header = header.replaceAll("%player%", player.getDisplayName());
        footer = footer.replaceAll("%player%", player.getDisplayName());

        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
        IChatBaseComponent tabFoot = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
        PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);
        try {
            Field field = headerPacket.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(headerPacket, tabFoot);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.sendPacket(headerPacket);
        }
    }
}
