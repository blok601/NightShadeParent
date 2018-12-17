package me.blok601.nightshadeuhc.util;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.blok601.nightshadeuhc.UHC;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 8/3/2017.
 */
public class ProxyUtil {

    public static void sendToServer(Player p, String sName){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(sName);
        p.sendPluginMessage(UHC.get(), "BungeeCord", out.toByteArray());
    }

}
