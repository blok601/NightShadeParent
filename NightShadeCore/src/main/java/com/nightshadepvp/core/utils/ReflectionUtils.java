package com.nightshadepvp.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;

public class ReflectionUtils {
    public static void sendPacket(Player p, Object packet) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, ClassNotFoundException {
        Object nmsPlayer = p.getClass().getMethod("getHandle").invoke(p);
        Object plrConnection = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
        plrConnection.getClass().getMethod("sendPacket", getNmsClass("Packet")).invoke(plrConnection, packet);
    }

    public static Class<?> getNmsClass(String nmsClassName) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "." + nmsClassName);
    }

    public static long[] elapsedTimeSince(Date date){
        Calendar now = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        start.setTime (date);

        long milliseconds1 = start.getTimeInMillis();
        long milliseconds2 = now.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return new long[]{
          diffSeconds, diffMinutes, diffHours, diffDays
        };
    }
}
