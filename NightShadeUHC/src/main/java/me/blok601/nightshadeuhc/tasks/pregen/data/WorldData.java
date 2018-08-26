package me.blok601.nightshadeuhc.tasks.pregen.data;

import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.*;
import java.util.*;

/**
 * World's file data management class.
 * <p>
 * By the way, this region file handler was created based on the divulged region file format: http://mojang.com/2011/02/16/minecraft-save-file-format-in-beta-1-3/
 *
 * @author WorldBorder, modified by LeonTG.
 */
public class WorldData {
    private final Map<CoordXZ, List<Boolean>> regionChunkExistence = Collections.synchronizedMap(new HashMap<CoordXZ, List<Boolean>>());

    private File[] files = null;
    private File folder = null;

    public static WorldData create(World world)  {
        WorldData newData = new WorldData();
        newData.folder = new File(world.getWorldFolder(), "region");

        if (!newData.folder.exists() || !newData.folder.isDirectory()) {
            File[] possibleDimFolders = world.getWorldFolder().listFiles(new DimFolderFileFilter());

            for (File possibleDimFolder : possibleDimFolders) {
                File possible = new File(world.getWorldFolder(), possibleDimFolder.getName() + File.separator + "region");

                if (possible.exists() && possible.isDirectory()) {
                    newData.folder = possible;
                    break;
                }
            }

            if (!newData.folder.exists() || !newData.folder.isDirectory()) {
                Core.get().getLogManager().log(Logger.LogType.SEVERE, "There was a problem loading a new data folder for world " + world.getName());
                return null;
            }
        }

        // Accepted region file formats: MCR is from late beta versions through 1.1, MCA is from 1.2+
        newData.files = newData.regionFolder().listFiles(new ExtFileFilter(".MCA"));

        if (newData.regionFiles() == null || newData.getRegionFileCount() == 0) {
            newData.files = newData.regionFolder().listFiles(new ExtFileFilter(".MCR"));

            if (newData.regionFiles() == null || newData.getRegionFileCount() == 0) {
                Core.get().getLogManager().log(Logger.LogType.SEVERE, "Couldn't find region files for world:" + world.getName());
                return null;
            }
        }

        return newData;
    }

    /**
     * Get the amount of region files there are for the world.
     *
     * @return Region file count.
     */
    private int getRegionFileCount() {
        return files.length;
    }

    /**
     * Get the folder where world's region files are located.
     *
     * @return The folder.
     */
    private File regionFolder() {
        return folder;
    }

    /**
     * Get a cloned array of all the region files.
     *
     * @return Array of region files.
     */
    private File[] regionFiles() {
        return files.clone();
    }

    /**
     * Get a region file by the given index.
     *
     * @param index The index.
     * @return The region file if any.
     */
    private File regionFile(int index) {
        if (getRegionFileCount() < index) {
            return null;
        }

        return files[index];
    }

    /**
     * Get the X and Z world coordinates of the region from the filename.
     *
     * @param index The index to use.
     * @return The region file coords.
     */
    private CoordXZ regionFileCoordinates(int index) {
        File regionFile = this.regionFile(index);
        String[] coords = regionFile.getName().split("\\.");

        int x, z;

        try {
            x = Integer.parseInt(coords[1]);
            z = Integer.parseInt(coords[2]);

            return new CoordXZ(x, z);
        } catch (Exception ex) {
            Bukkit.getLogger().warning("Error! Region file found with abnormal name: " + regionFile.getName());
            return null;
        }
    }

    /**
     * Find out if the chunk at the given coordinates exists.
     *
     * @param x The X coord.
     * @param z The Z coord.
     *
     * @return True if it is, false otherwise.
     */
    private boolean doesChunkExist(int x, int z) {
        CoordXZ region = new CoordXZ(CoordXZ.chunkToRegion(x), CoordXZ.chunkToRegion(z));
        List<Boolean> regionChunks = this.getRegionData(region);

        return regionChunks.get(coordToRegionOffset(x, z));
    }

    /**
     * Find out if the chunk at the given coordinates has been fully generated.
     * Minecraft only fully generates a chunk when adjacent chunks are also loaded.
     *
     * @param x The chunk X coord.
     * @param z The chunk Z coord.
     *
     * @return True if it is, false otherwise.
     */
    public boolean isChunkFullyGenerated(int x, int z) {
        return !(!doesChunkExist(x, z) || !doesChunkExist(x+1, z) || !doesChunkExist(x-1, z) || !doesChunkExist(x, z+1) || !doesChunkExist(x, z-1));
    }

    /**
     * Let us know a chunk has been generated, to update our region map.¨
     *
     * @param x The chunk X coord.
     * @param z The chunk Z coord.
     */
    public void chunkExistsNow(int x, int z) {
        CoordXZ region = new CoordXZ(CoordXZ.chunkToRegion(x), CoordXZ.chunkToRegion(z));
        List<Boolean> regionChunks = getRegionData(region);

        regionChunks.set(coordToRegionOffset(x, z), true);
    }

    /**
     * Make the given coords into a region offset.
     * <p>
     * A region is 32 * 32 chunks; chunk pointers are stored in region file at
     * position: x + z*32 (32 * 32 chunks = 1024)
     * input x and z values can be world-based chunk coordinates or
     * local-to-region chunk coordinates either one.
     *
     * @param x The X coord.
     * @param z The Z coord.
     *
     * @return The offset.
     */
    private int coordToRegionOffset(int x, int z) {
        // "%" modulus is used to convert potential world coordinates to definitely be local region coordinates
        x = x % 32;
        z = z % 32;

        if (x < 0) x += 32;
        if (z < 0) z += 32;

        return (x + (z * 32));
    }

    /**
     * Get the region data for the given coords.
     *
     * @param region The region coords.
     * @return A list of boolean data.
     */
    private List<Boolean> getRegionData(CoordXZ region) {
        List<Boolean> data = regionChunkExistence.get(region);

        if (data != null) {
            return data;
        }

        data = new ArrayList<>(1024);

        for (int i = 0; i < 1024; i++) {
            data.add(Boolean.FALSE);
        }

        for (int i = 0; i < files.length; i++) {
            CoordXZ coord = regionFileCoordinates(i);

            if (!coord.equals(region)) {
                continue;
            }

            try {
                RandomAccessFile regionData = new RandomAccessFile(regionFile(i), "r");

                for (int j = 0; j < 1024; j++) {
                    if (regionData.readInt() != 0) {
                        data.set(j, true);
                    }
                }

                regionData.close();
            } catch (FileNotFoundException ex) {
                Bukkit.getLogger().warning("Error! Could not open region file to find generated chunks: " + regionFile(i).getName());
            } catch (IOException ex) {
                Bukkit.getLogger().warning("Error! Could not read region file to find generated chunks: " + regionFile(i).getName());
            }
        }

        regionChunkExistence.put(region, data);
        return data;
    }

    /**
     * File filter used for region files.
     *
     * @author WorldBorder
     */
    private static class ExtFileFilter implements FileFilter {
        String ext;

        ExtFileFilter(String extension) {
            this.ext = extension.toLowerCase();
        }

        @Override
        public boolean accept(File file) {
            return (file.exists() && file.isFile() && file.getName().toLowerCase().endsWith(ext));
        }
    }

    /**
     * File filter used for DIM* folders (for nether, End, and custom world types)
     *
     * @author WorldBorder
     */
    private static class DimFolderFileFilter implements FileFilter {
        @Override
        public boolean accept(File file) {
            return (file.exists() && file.isDirectory() && file.getName().toLowerCase().startsWith("dim"));
        }
    }
}