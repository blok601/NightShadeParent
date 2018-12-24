package me.blok601.nightshadeuhc.task;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Blok on 11/11/2018.
 */
public class StaffTrackTask extends BukkitRunnable {
    @Override
    public void run() {
        if(!GameState.gameHasStarted()) return;
        NSPlayer nsPlayer;
        for (UHCPlayer uhcPlayer : UHCPlayerColl.get().getAllOnline()){
            if(!uhcPlayer.isSpectator()) continue;
            nsPlayer = NSPlayer.get(uhcPlayer.getPlayer());
            if(nsPlayer.isAFK()) continue;
            if(nsPlayer.hasRank(Rank.TRIAL)){
                nsPlayer.setSpectatingTime(nsPlayer.getSpectatingTime() + 5);
            }
        }
    }
}
