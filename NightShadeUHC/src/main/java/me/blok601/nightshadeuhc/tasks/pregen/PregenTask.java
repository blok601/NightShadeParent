package me.blok601.nightshadeuhc.tasks.pregen;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.utils.*;
import me.blok601.nightshadeuhc.tasks.pregen.data.CoordXZ;
import me.blok601.nightshadeuhc.tasks.pregen.data.WorldData;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by Blok on 1/20/2018.
 *
 * @author WorldBorder, modifed by LeonTG for ArcticMC, modified by Blok for NightShadePvP
 */
public class PregenTask extends BukkitRunnable{

    private Runnable callback;

    private static final String PREGEN_MSG = ChatUtils.format("&§Pregen&§» §a%s Complete");

    private static final int AUTOSAVE_FREQUENCY = 30;
    private static final int EXTRA_DISTANCE = 208;

    public static final int MEMORY_TOLLERANCE = 500;
    public static final int MEMORY_TOO_LOW = 200;

    public static final int TPS_TOLLERANCE = 17;
    public static final int TPS_TOO_LOW = 11;

    private static final Runtime RUNTIME = Runtime.getRuntime();
    private final UHC plugin;

    private final WorldData worldData;
    private final String worldName;

    private boolean pausedForMemory = false;
    private boolean pausedForTPS = false;

    private boolean readyToGo;
    private boolean paused = false;

    private int chunksPerRun;

    private boolean forceLoad;

    private final int radius;

    private final int minX;
    private final int maxX;

    private final int minZ;
    private final int maxZ;

    private CoordXZ lastChunk = new CoordXZ(0, 0);
    private int chunkX;
    private int chunkZ;

    private int tickFrequency;
    private int refX = 0, lastLegX = 0;
    private int refZ = 0, lastLegZ = 0;
    private int refLength = -1;
    private int refTotal = 0, lastLegTotal = 0;

    private boolean isZLeg = false;
    private boolean isNeg = false;

    private int length = -1;
    private int current = 0;

    private boolean insideBorder = true;

    private List<CoordXZ> storedChunks = Lists.newLinkedList();
    private Set<CoordXZ> originalChunks = Sets.newHashSet();

    private long lastAutosave = System.currentTimeMillis();
    private long lastReport = System.currentTimeMillis();

    private int reportTarget;
    private int reportTotal = 0;
    private int reportNum = 0;

    public PregenTask(World world, int radius, int speed, int ticks, boolean forceLoadChunks, Runnable callback) {
        this.worldName = world.getName();
        this.radius = radius + EXTRA_DISTANCE;
        this.tickFrequency= ticks;
        this.chunksPerRun = speed;
        this.forceLoad = forceLoadChunks;
        this.plugin = UHC.get();
        this.callback = callback;

        WorldBorder border = world.getWorldBorder();

        this.minX = border.getCenter().getBlockX() - this.radius;
        this.maxX = border.getCenter().getBlockX() + this.radius;

        this.minZ = border.getCenter().getBlockZ() - this.radius;
        this.maxZ = border.getCenter().getBlockZ() + this.radius;

        this.chunkX = CoordXZ.blockToChunk(border.getCenter().getBlockX());
        this.chunkZ = CoordXZ.blockToChunk(border.getCenter().getBlockZ());

        this.worldData = WorldData.create(world); // Gen region files

        int chunkWidthX = (int) Math.ceil((double) ((this.radius + 16) * 2) / 16); // Get rounded up chunk distance with 1 extra (with padding)
        int chunkWidthZ = (int) Math.ceil((double) ((this.radius + 16) * 2) / 16); //Same

        int biggerWidth = (chunkWidthX > chunkWidthZ) ? chunkWidthX : chunkWidthZ; //Get whatever is bigger

        this.reportTarget = (biggerWidth * biggerWidth) + biggerWidth + 1;

        Chunk[] originals = world.getLoadedChunks(); //Skip these for max performance :) (and use to start)
        for (Chunk chunk : originals){
            originalChunks.add(new CoordXZ(chunk.getX(), chunk.getZ()));
        }

        this.readyToGo = true; //Ready to start
    }

    @Override
    public void run() {
        int mem = getAvailableMemory();

        if(pausedForMemory){
            if(mem  < MEMORY_TOLLERANCE){
                return;
            }

            pausedForMemory = false;
            readyToGo = true;
            //TODO: Broadcast messages unpause mem

        }

        if(pausedForTPS){
            if(Lag.getTPS() <= TPS_TOLLERANCE){
                return;
            }

            pausedForTPS = false;
            readyToGo = true;
            //TODO: Broadcast messages unpaused tps
        }

        if(!readyToGo || paused){
            return;
        }

        readyToGo = false;

        long loopStartTime = System.currentTimeMillis();

        World world = Bukkit.getWorld(worldName); //Incase world nulls  after being passed in
        if(world == null){
            Util.staffLog("&cPregen couldn't be started because the world wasn't found!");
            cancel();
            return;
        }


        for (int loop = 0; loop < chunksPerRun; loop++){
            if(isPaused()){
                return;
            }

            long now = System.currentTimeMillis();
            if(now > lastReport + 5000){
                lastReport = System.currentTimeMillis();
                actionBarUpdate(world);
                reportTotal += reportNum;
                reportNum = 0;

                if (lastAutosave + (AUTOSAVE_FREQUENCY * 1000) < lastReport) {
                    lastAutosave = lastReport;

                    Util.staffLog("&eSaving the world to be safe!");
                    world.save();
                    Util.staffLog("&aWorld Saved!");
                }

                if(mem < MEMORY_TOO_LOW){
                    pausedForMemory = true;
                    storeSettings();
                    Util.staffLog("&cThe server doesn't have enough memory to keep pregenning! The pregen will resume shortly!");

                    System.gc(); // Garabage collector woop woop
                }

                if(Lag.getTPS() <= TPS_TOO_LOW){
                    pausedForTPS = true;
                    storeSettings();
                    Util.staffLog("&cThe server's TPS have dropped to low to continue pregenning! The pregen will resume shortly!");

                }
            }

            actionBarUpdate(world);

            if(now > loopStartTime + 45){
                readyToGo = true;
                return;
            }

            while(!isInsideBorder(CoordXZ.chunkToBlock(chunkX) + 8, CoordXZ.chunkToBlock(chunkZ) + 8)){
                if(!moveToNext(world)){
                    return;
                }
            }

            insideBorder = true;

            if(!forceLoad){
                while (worldData.isChunkFullyGenerated(chunkX, chunkZ)) {
                    insideBorder = true;

                    if (!moveToNext(world)) {
                        return;
                    }
                }
            }

            world.loadChunk(chunkX, chunkZ, true);
            worldData.chunkExistsNow(chunkX, chunkZ);

            int popX = !isZLeg ? chunkX : (chunkX + (isNeg ? -1 : 1));
            int popZ = isZLeg ? chunkZ : (chunkZ + (!isNeg ? -1 : 1));

            world.loadChunk(popX, popZ, false); //Get surrounding chunks so it can generate

            if (!storedChunks.contains(lastChunk) && !originalChunks.contains(lastChunk)) {
                world.loadChunk(lastChunk.getX(), lastChunk.getZ(), false);
                storedChunks.add(new CoordXZ(lastChunk.getX(), lastChunk.getZ()));
            }

            storedChunks.add(new CoordXZ(popX, popZ));
            storedChunks.add(new CoordXZ(chunkX, chunkZ));

            while (storedChunks.size() > 8) {
                CoordXZ coord = storedChunks.remove(0);

                if (!originalChunks.contains(coord)) {
                    world.unloadChunkRequest(coord.getX(), coord.getZ());
                }
            }

            if (!moveToNext(world)) {
                return;
            }

            readyToGo = true;

        }



    }

    /**
     * Get the current available memory in a int form.
     *
     * @return Available memory.
     */
    public static int getAvailableMemory() {
        return (int) (((RUNTIME.maxMemory() - RUNTIME.totalMemory()) + RUNTIME.freeMemory()) / 1048576);
    }

    boolean isPaused() {
        return paused || pausedForMemory || pausedForTPS;
    }

    /**
     * Store all the pregen task settings to the data config.
     */
    private void storeSettings() {
        FileConfiguration config = plugin.getConfig();

        config.set("pregen task.world", worldName);
        config.set("pregen task.radius", radius);
        config.set("pregen task.chunksPerRun", chunksPerRun);
        config.set("pregen task.tickFrequency", tickFrequency);
        config.set("pregen task.x", refX);
        config.set("pregen task.z", refZ);
        config.set("pregen task.length", refLength);
        config.set("pregen task.total", refTotal);
        config.set("pregen task.forceLoad", forceLoad);

        plugin.saveConfig();
    }

    /**
     * Check if the given X and Z are inside the pregen border.
     *
     * @param xLoc The X loc to use.
     * @param zLoc The Z loc to use.
     *
     * @return True if it is, false otherwise.
     */
    private boolean isInsideBorder(double xLoc, double zLoc) {
        return !(xLoc < minX || xLoc > maxX || zLoc < minZ || zLoc > maxZ);
    }

    /**
     * Move to a new chunk to pregen.
     *
     * @param world The world to move in.
     * @return It's success.
     */
    private boolean moveToNext(World world) {
        if (isPaused()) {
            return false;
        }

        reportNum++;

        if (!isNeg && current == 0 && length > 3) {
            if (!isZLeg) {
                lastLegX = chunkX;
                lastLegZ = chunkZ;
                lastLegTotal = reportTotal + reportNum;
            } else {
                refX = lastLegX;
                refZ = lastLegZ;
                refTotal = lastLegTotal;
                refLength = length - 1;
            }
        }

        if (current < length) {
            current++;
        } else {
            current = 0;
            isZLeg ^= true;

            if (isZLeg) {
                isNeg ^= true;
                length++;
            }
        }

        lastChunk.setX(chunkX);
        lastChunk.setZ(chunkZ);

        if (isZLeg) {
            chunkZ += (isNeg) ? -1 : 1;
        } else {
            chunkX += (isNeg) ? -1 : 1;
        }

        if (isZLeg && isNeg && current == 0) {
            if (!insideBorder) {
                paused = true;

                world.save();
                cancel();

                PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"The pregen has finished!\",\"color\":\"dark_aqua\",\"bold\":true}"));
                Bukkit.getOnlinePlayers().forEach((Consumer<Player>) player -> {
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                });
                Core.get().getLogManager().log(Logger.LogType.INFO, "The pregen for world: " + world.getName() + " has finished!");
                Bukkit.getConsoleSender().sendMessage(getMessage(world, 100));
                if(callback != null) callback.run();
                return false;
            } else {
                insideBorder = false;
            }
        }

        return true;
    }

    /**
     * Get the message used for the console/action bar with the given world's name.
     *
     * @param world The world to use.
     * @return The message.
     */
    private String getMessage(World world) {
        return getMessage(world, (((double) (reportTotal + reportNum) / (double) reportTarget) * 100));
    }

    /**
     * Get the message used for the console/action bar with the given world's name with the given completed percent.
     *
     * @param world The world to use.
     * @param perc The percent to display.
     *
     * @return The message.
     */
    private String getMessage(World world, double perc) {
        return String.format(PREGEN_MSG, MathUtils.formatDoubleToPercet(perc) + "%");
    }

    /**
     * Set the paused state of the pregen task to the given state.
     * <p>
     * If the given state is false, paused for memory & tps will be unpaused.
     *
     * @param pause The new paused state.
     */
    private void pause(boolean pause) {
        if (pausedForMemory && !pause) {
            pausedForMemory = false;
        }
        else if (pausedForTPS && !pause) {
            pausedForTPS = false;
        }
        else {
            paused = pause;
        }

        if (paused) {
            storeSettings();
        } else {
            unstoreSettings();
        }
    }

    void unstoreSettings() {
        plugin.getConfig().set("pregen task", null);
        plugin.saveConfig();
    }

    void actionBarUpdate(World world){
        Bukkit.getOnlinePlayers().stream().filter(o -> NSPlayer.get(o.getUniqueId()) != null && NSPlayer.get(o.getUniqueId()).hasRank(Rank.TRIAL)).forEach(o -> {
            ActionBarUtil.sendActionBarMessage(o, getMessage(world), 1, UHC.get());
        });
    }

}
