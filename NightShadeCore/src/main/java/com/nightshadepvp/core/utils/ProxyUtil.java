package com.nightshadepvp.core.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.nightshadepvp.core.Core;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 8/3/2017.
 */
public class ProxyUtil {

    public static void sendToServer(Player p, String sName){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(sName);
        p.sendPluginMessage(Core.get(), "BungeeCord", out.toByteArray());
    }

    public static boolean isBetween(int max, int min, int val) {
        return min < val && val < max;
    }

}
