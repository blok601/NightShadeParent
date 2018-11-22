package com.nightshadepvp.meetup.utils;

import org.bukkit.ChatColor;

/**
 * Created by Blok on 10/15/2018.
 */
public class ChatUtils {

    public static String PREFIX = format("&5Meetup&8» ");

    public static String format(String input){
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static String message(String input){
        return format(PREFIX + input);
    }
}
