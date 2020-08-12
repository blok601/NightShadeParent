package com.nightshadepvp.nightcheat.utils;

public class LocationUtils {


    public static final double distanceSquared(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        final double dx = Math.abs(x1 - x2);
        final double dy = Math.abs(y1 - y2);
        final double dz = Math.abs(z1 - z2);
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Yaw (angle in grad) difference. This ensures inputs are interpreted
     * correctly (for 360 degree offsets).
     *
     * @param fromYaw
     *            the from yaw
     * @param toYaw
     *            the to yaw
     * @return Angle difference to get from fromYaw to toYaw. Result is in
     *         [-180, 180].
     */
    public static final float yawDiff(float fromYaw, float toYaw){
        if (fromYaw <= -360f) {
            fromYaw = -((-fromYaw) % 360f);
        }
        else if (fromYaw >= 360f) {
            fromYaw = fromYaw % 360f;
        }
        if (toYaw <= -360f) {
            toYaw = -((-toYaw) % 360f);
        }
        else if (toYaw >= 360f) {
            toYaw = toYaw % 360f;
        }
        float yawDiff = toYaw - fromYaw;
        if (yawDiff < -180f) {
            yawDiff += 360f;
        }
        else if (yawDiff > 180f) {
            yawDiff -= 360f;
        }
        return yawDiff;
    }
}
