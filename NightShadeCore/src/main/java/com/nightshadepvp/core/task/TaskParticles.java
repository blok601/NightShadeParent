package com.nightshadepvp.core.task;

import com.massivecraft.massivecore.ModuloRepeatTask;
import com.nightshadepvp.core.entity.MConf;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.NSPlayerColl;
import com.nightshadepvp.core.entity.objects.PlayerEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Blok on 4/14/2018.
 */
public class TaskParticles extends BukkitRunnable {

    @Override
    public void run() {

    }

    //    private static TaskParticles i = new TaskParticles();
//
//    public static TaskParticles get() {
//        return i;
//    }
//
//
//    @Override
//    public long getDelayMillis() {
//        return MConf.get().particleDelayTaskMillis;
//    }
//
//    @Override
//    public void invoke(long l) {
//
//        Location location;
//
//        for (NSPlayer user : NSPlayerColl.get().getAllOnline()) {
//
//            if (user.getEffect() != null && user.getEffect() != PlayerEffect.NONE) {
//
//                location = user.getPlayer().getLocation();
//                location.setY(location.getY() + 0.5);
//
//                PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(user.getEffect().getEffect(), true, (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0, 0, 0, 0, 0);
//                ((CraftPlayer) user.getPlayer()).getHandle().playerConnection.sendPacket(particles);
//            }
//        }
//
//    }
}
