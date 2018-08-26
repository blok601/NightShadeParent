package me.blok601.nightshadeuhc.tasks.pregen.data;

/**
 * Simple storage class for chunk x/z values.
 *
 * @author WorldBorder, modified by LeonTG for ArcticMC, modified by Blok for NightShadePvP
 */
public class CoordXZ {
    private int x;
    private int z;

    public CoordXZ(int x, int z) {
        this.x = x;
        this.z = z;
    }

    /**
     * Set the X of the coord storage.
     *
     * @param x The new x.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Get the x coord of this coord storage.
     *
     * @return The x coord.
     */
    public int getX() {
        return x;
    }

    /**
     * Set the Z of the coord storage.
     *
     * @param z The new z.
     */
    public void setZ(int z) {
        this.z = z;
    }

    /**
     * Get the z coord of this coord storage.
     *
     * @return The z coord.
     */
    public int getZ() {
        return z;
    }

    /**
     * Make the given block number into a chunk number.
     *
     * @param blockVal The block number.
     * @return The chunk number.
     */
    public static int blockToChunk(int blockVal) {
        return blockVal >> 4;
    }

    /**
     * Make the given block number into a region number.
     *
     * @param regionVal The block number.
     * @return The region number.
     */
    public static int blockToRegion(int blockVal) {
        return blockVal >> 9;
    }

    /**
     * Make the given chunk number into a region number.
     *
     * @param chunkVal The chunk number.
     * @return The region number.
     */
    static int chunkToRegion(int chunkVal) {
        return chunkVal >> 5;
    }

    /**
     * Make the given chunk number into a block number.
     *
     * @param chunkVal The chunk number.
     * @return The block number.
     */
    public static int chunkToBlock(int chunkVal) {
        return chunkVal << 4;
    }

    /**
     * Make the given region number into a block number.
     *
     * @param regionVal The region number.
     * @return The block number.
     */
    public static int regionToBlock(int regionVal) {
        return regionVal << 9;
    }

    /**
     * Make the given region number into a chunk number.
     *
     * @param regionVal The region number.
     * @return The chunk number.
     */
    public static int regionToChunk(int regionVal) {
        return regionVal << 5;
    }

    @Override
    public String toString() {
        return this.x + ":" + this.z;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        else if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        CoordXZ other = (CoordXZ) obj;
        return getX() == other.getX() && getZ() == other.getZ();
    }

    @Override
    public int hashCode() {
        return (getX() << 9) + getZ();
    }
}
