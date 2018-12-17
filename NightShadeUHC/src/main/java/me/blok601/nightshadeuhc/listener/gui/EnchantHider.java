package me.blok601.nightshadeuhc.listener.gui;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * Created by Blok on 8/20/2018.
 */
public class EnchantHider extends PacketAdapter {

    public EnchantHider(JavaPlugin plugin){
        super(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.WINDOW_DATA);
    }

    @Override
    public void onPacketSending(PacketEvent e){
        if(e.getPacketType() == PacketType.Play.Server.WINDOW_DATA){
            PacketContainer packet = e.getPacket();
            List<Integer> data = packet.getIntegers().getValues();

            if(data.get(1) == 4 || data.get(1) == 5 || data.get(1) == 6){
                e.setCancelled(true); //Cancel the enchant packet send
            }

        }
    }

}
