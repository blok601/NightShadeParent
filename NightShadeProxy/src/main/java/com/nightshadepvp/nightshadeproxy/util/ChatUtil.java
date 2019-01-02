package com.nightshadepvp.nightshadeproxy.util;


import net.md_5.bungee.api.chat.TextComponent;

/**
 * Created by Blok on 1/2/2019.
 */
public class ChatUtil {

    private static final String PREFIX = "§5NightShade&8» ";

    public static TextComponent format(String string) {
        return new TextComponent(string.replaceAll("&", "§"));
    }

    public static TextComponent message(String input) {
        return format(PREFIX + input);
    }


}
