package me.blok601.nightshadeuhc.util;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.NSPlayerColl;
import me.blok601.nightshadeuhc.entity.UHCPlayer;

public class FreezeUtil {

    public static void start(){
        UHCPlayer uhcPlayer;
        for (NSPlayer user : NSPlayerColl.get().getAllOnline()) {
            uhcPlayer = UHCPlayer.get(user.getUuid());

            if (uhcPlayer == null) continue;

			if(user.hasRank(Rank.TRIAL)){
                user.msg(ChatUtils.message("&eYou were not frozen since you are a staff member!"));
				continue;
			}

            if (uhcPlayer.isSpectator()) {
				continue;
			}

            user.msg(ChatUtils.message("&eYou have been frozen!"));
		}
	}

	public static void stop(){
        for (NSPlayer nsPlayer : NSPlayerColl.get().getAllOnline()) {
            if (nsPlayer == null) continue;
            if (nsPlayer.isFrozen()) {
                nsPlayer.setFrozen(false);
                nsPlayer.msg(ChatUtils.message("&eYou were unfrozen!"));
			}
		}
	}

}
