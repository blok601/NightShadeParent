package com.nightshadepvp.core.utils;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayerColl;
import com.nightshadepvp.core.entity.objects.PlayerColor;
import com.nightshadepvp.core.entity.objects.PlayerEffect;
import org.bukkit.ChatColor;

import java.util.ArrayList;


/**
 * Created by Master on 8/27/2017.
 */
public class ChatUtils {

    public static final String PREFIX = format("&5NightShade&8» ");

    public static String message(String msg){
        return ChatColor.translateAlternateColorCodes('&', PREFIX + msg);
    }

    public static String format(String msg){
        return ChatColor.translateAlternateColorCodes('&', msg);
    }


    public static String arrayListSerializer(ArrayList<PlayerColor> list){
        StringBuilder string = new StringBuilder();
        for (PlayerColor color : list){
            string.append(color.toString().toLowerCase()).append(":");
        }

        return string.substring(0, string.length());
    }

    public static ArrayList<PlayerColor> deserializeArrayList(String string){
        if(!string.contains(":")){
            return new ArrayList<PlayerColor>();
        }
        //Empty arraylist
        String[] strings = string.split(":");
        ArrayList<PlayerColor> colors = new ArrayList<>();
        for (String s : strings){
            PlayerColor c = PlayerColor.valueOf(s.toUpperCase());
            if(c == null) continue;

            colors.add(c);
        }

        return colors;
    }


    public static ArrayList<PlayerEffect> deseralizeEffectList(String string){
        if(!string.contains(":")){
            return new ArrayList<PlayerEffect>();
        }
        //Empty arraylist
        String[] strings = string.split(":");
        ArrayList<PlayerEffect> colors = new ArrayList<>();
        for (String s : strings){
            PlayerEffect c = PlayerEffect.valueOf(s.toUpperCase());
            if(c == null) continue;

            colors.add(c);
        }

        return colors;
    }

    public static String arrayListEffectSerializer(ArrayList<PlayerEffect> list){
        StringBuilder string = new StringBuilder();
        for (PlayerEffect color : list){
            string.append(color.toString().toLowerCase()).append(":");
        }

        return string.substring(0, string.length());
    }

    public static void sendAll(String message, Rank allowed){
        NSPlayerColl.get().getAllOnline().stream().filter(nsPlayer -> nsPlayer.hasRank(allowed)).forEach(nsPlayer -> nsPlayer.msg(ChatUtils.message(message)));
    }

}
