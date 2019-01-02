package com.nightshadepvp.core;

import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Bukkit;

/**
 * Created by Blok on 10/27/2017.
 */
public class Logger {


    public void log(LogType type, String message){
        Bukkit.getConsoleSender().sendMessage(type.getPrefix() + type.getColor() + ChatUtils.format(message));
    }

    public enum LogType{

        WARNING("&8[&cWarning&8] ", "&c"), INFO("&8[&eInfo&8] ", "&e"), SERVER("&8[&aServer&8] ", "&a"), SEVERE("&8[&4Severe&8] ", "&4"), DEBUG("&8[&2Debug&8]", "&2");

        private String prefix;
        private String color;


        LogType(String prefix, String color){
            this.prefix = ChatUtils.format(prefix);
            this.color = ChatUtils.format(color);
        }

        public String getPrefix() {
            return prefix;
        }

        public String getColor() {
            return color;
        }
    }

}
