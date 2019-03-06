package com.nightshadepvp.core.utils;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Blok on 3/5/2019.
 */
public class PacketUtils {

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
            ProtocolLibrary.getProtocolManager().sendServerPacket(target, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
