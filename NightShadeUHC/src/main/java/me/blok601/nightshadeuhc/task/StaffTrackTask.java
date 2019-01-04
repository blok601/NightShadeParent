package me.blok601.nightshadeuhc.task;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.NSPlayerColl;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Blok on 11/11/2018.
 */
public class StaffTrackTask extends BukkitRunnable {

    @Override
    public void run() {

        if (!GameState.gameHasStarted()) return;

        NSPlayerColl.get().getAllOnline().stream().filter(nsPlayer -> !nsPlayer.isAFK() &&  nsPlayer.hasRank(Rank.TRIAL) && UHCPlayer.get(nsPlayer.getPrefix()).isSpectator()).forEach(nsPlayer ->
                nsPlayer.setSpectatingTime(nsPlayer.getSpectatingTime() + 5));

    }

}
