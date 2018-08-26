package me.blok601.nightshadeuhc.tasks;

import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Blok on 3/16/2017.
 */
public class FinalHealTask extends BukkitRunnable {

    private int counter;

    public FinalHealTask(int counter) {
        this.counter = counter;
    }


    @Override
    public void run() {
        if (counter == 0) {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.setHealth(pl.getMaxHealth());
                pl.setFoodLevel(20);
            }
//            ChatUtils.sendAll("&cThe final heal has been given! Don't ask for another!");
            PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"Final Heal Has Been Given!\",\"color\":\"dark_aqua\",\"bold\":true}"), 0, 20, 0);
            PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"Don't ask for another\",\"color\":\"dark_red\"}"), 0, 20, 0);
            for (UHCPlayer uhcPlayer : UHCPlayerColl.get().getAllOnline()) {
                if (uhcPlayer.isUsingOldVersion()) {
                    uhcPlayer.msg(ChatUtils.message("&3Final heal has been given!"));
                    continue;
                }
                ((CraftPlayer) uhcPlayer.getPlayer()).getHandle().playerConnection.sendPacket(title);
                ((CraftPlayer) uhcPlayer.getPlayer()).getHandle().playerConnection.sendPacket(subtitle);
            }


            counter = -10;
            cancel();
        } else {
            counter--;
        }

    }

}
