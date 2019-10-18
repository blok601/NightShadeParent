package me.blok601.nightshadeuhc.task;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.event.MeetupStartEvent;
import me.blok601.nightshadeuhc.util.ChatUtils;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Blok on 3/16/2017.
 */
public class MeetupTask extends BukkitRunnable {

    private int counter;

    public MeetupTask(int counter) {
        this.counter = counter;
    }


    @Override
    public void run() {

        if (counter <= -1) return;

        if (counter == 0) {

            Bukkit.getServer().getPluginManager().callEvent(new MeetupStartEvent());
            GameState.setState(GameState.MEETUP);

            PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"Meetup!\",\"color\":\"dark_purple\",\"bold\":true}"), 0, 20, 0);
            PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"Head Toward 0, 0 Now!\",\"color\":\"aqua\"}"), 0, 20, 0);

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

                onlinePlayer.sendMessage(ChatUtils.format("&5&m-----------------------------------"));
                onlinePlayer.sendMessage(ChatUtils.format("&3Meetup is now!"));
                onlinePlayer.sendMessage(ChatUtils.format("&3Head to 0,0!"));
                onlinePlayer.sendMessage(ChatUtils.format("&5&m-----------------------------------"));

                ((CraftPlayer) onlinePlayer).getHandle().playerConnection.sendPacket(title);
                ((CraftPlayer) onlinePlayer).getHandle().playerConnection.sendPacket(subtitle);

            }

            counter = -1;
            cancel();

        }

        counter--;

    }

}
