package com.nightshadepvp.nightcheat.cheat.combat;

import com.nightshadepvp.nightcheat.cheat.Cheat;
import com.nightshadepvp.nightcheat.cheat.CheatType;
import com.nightshadepvp.nightcheat.utils.LocationUtils;
import org.bukkit.Location;

import java.util.UUID;

public class AngleCheat extends Cheat {

    public static class AttackLocation {
        public final double x, y, z;
        /** Yaw of the attacker. */
        public final float yaw;
        public long time;
        public final UUID damagedId;
        /** Squared distance to the last location (0 if none given). */
        public final double distSqLast;
        /** Difference in yaw to the last location (0 if none given). */
        public final double yawDiffLast;
        /** Time difference to the last location (0 if none given). */
        public final long timeDiff;
        /** If the id differs from the last damaged entity (true if no lastLoc is given). */
        public final boolean idDiffLast;
        public AttackLocation(final Location loc, final UUID damagedId, final long time, final AttackLocation lastLoc) {
            x = loc.getX();
            y = loc.getY();
            z = loc.getZ();
            yaw = loc.getYaw();
            this.time = time;
            this.damagedId = damagedId;

            if (lastLoc != null) {
                distSqLast = LocationUtils.distanceSquared(x, y, z, lastLoc.x, lastLoc.y, lastLoc.z);
                yawDiffLast = LocationUtils.yawDiff(yaw, lastLoc.yaw);
                timeDiff = Math.max(0L, time - lastLoc.time);
                idDiffLast = !damagedId.equals(lastLoc.damagedId);
            } else {
                distSqLast = 0.0;
                yawDiffLast = 0f;
                timeDiff = 0L;
                idDiffLast = true;
            }
        }
    }

    public static long maxTimeDiff = 1000L;

    public AngleCheat() {
        super("Angle", CheatType.COMBAT);
    }



}
