package com.nightshadepvp.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 9/2/2017.
 */
public class LocationUtils {

    public static String locationToString(Location location){
        StringBuilder builder = new StringBuilder();

        builder.append(String.valueOf(location.getX())).append(":");
        builder.append(String.valueOf(location.getY())).append(":");
        builder.append(String.valueOf(location.getZ())).append(":");
        builder.append(location.getWorld().getName());

        return builder.toString();
    }

    public static String locationToString(Player player){
        return locationToString(player.getLocation());
    }

    public static Location locationFromString(String string){
        String[] args = string.split(":");

        double x = Double.parseDouble(args[0]);
        double y = Double.parseDouble(args[1]);
        double z = Double.parseDouble(args[2]);
        World world = Bukkit.getWorld(args[3]);

        return new Location(world, x, y, z);
    }

}
