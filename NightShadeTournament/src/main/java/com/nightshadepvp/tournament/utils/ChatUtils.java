package com.nightshadepvp.tournament.utils;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.tournament.entity.TPlayerColl;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * Created by Blok on 6/12/2018.
 */
public class ChatUtils {

    private static final String PREFIX = ChatColor.DARK_PURPLE + "Tournament" + ChatColor.DARK_GRAY + "» ";

    public static String format(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String message(String string){
        return format(PREFIX + string);
    }

    public static void staffLog(String string){
        Bukkit.getOnlinePlayers().stream().filter(o -> NSPlayer.get(o.getUniqueId()).hasRank(Rank.TRIAL)).forEach(o -> o.sendMessage(ChatUtils.format("&8[&bStaff&8]&r " + string)));
    }

    public static void broadcast(String msg){
        TPlayerColl.get().getAllOnline().forEach(tPlayer -> tPlayer.msg(ChatUtils.message(msg)));
    }
}
