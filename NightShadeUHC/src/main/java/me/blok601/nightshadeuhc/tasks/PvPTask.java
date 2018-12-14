package me.blok601.nightshadeuhc.tasks;

import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.commands.extras.PvPCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.events.PvPEnableEvent;
import me.blok601.nightshadeuhc.utils.ActionBarUtil;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Consumer;

/**
 * Created by Blok on 3/16/2017.
 */
public class PvPTask extends BukkitRunnable {
    public static int counter;
    private World w;

    public PvPTask(int counter, World w) {
        PvPTask.counter = counter;
        this.w = w;
    }

    @Override
    public void run() {
        if (counter == 0) {
            PvPCommand.enablePvP(w);
            Bukkit.getServer().getPluginManager().callEvent(new PvPEnableEvent());
            PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"PvPCommand Has Been Enabled!\",\"color\":\"dark_aqua\",\"bold\":true}"));
            for (UHCPlayer uhcPlayer : UHCPlayerColl.get().getAllOnline()) {
                if (uhcPlayer.isUsingOldVersion()) {
                    uhcPlayer.msg(ChatUtils.message("&3PvP has been enabled!"));
                    continue;
                }

                ((CraftPlayer) uhcPlayer.getPlayer()).getHandle().playerConnection.sendPacket(packet);
            }
            counter = -10;
            this.cancel();
        } else {
            Bukkit.getOnlinePlayers().forEach((Consumer<Player>) player -> {
                ActionBarUtil.sendActionBarMessage(player, "§5PvP enabled in " + get(counter), 1, UHC.get());
            });
        }
        counter--;


    }

    private String get(int i){
        int m = i/60;
        int s = i%60;

        return "§3" + m + "§5m§3" + s + "§5s";
    }
}
