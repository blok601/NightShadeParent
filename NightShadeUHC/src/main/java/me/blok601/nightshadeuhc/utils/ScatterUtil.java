package me.blok601.nightshadeuhc.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Blok on 9/24/2017.
 */
public class ScatterUtil {

    private static ThreadLocalRandom x = ThreadLocalRandom.current();

    public static void scatter(World world, int radius, List<Player> players) {
        boolean ground;
        boolean valid = false;
        Location scatterLocation = null;
        while(!valid) {
            Bukkit.getServer().getLogger().info("Calculating a potential spawn point.");
            scatterLocation = getRandomLocation(world, radius) ;

            ground = false;
            while(!ground) {
                Location testLocation = new Location(scatterLocation.getWorld(), scatterLocation.getX(), scatterLocation.getY()-1, scatterLocation.getZ());
                if(testLocation.getBlock().getType() == Material.AIR) {
                    scatterLocation=testLocation;
                } else {
                    if(scatterLocation.getBlock().getType() != Material.AIR
                            || scatterLocation.getBlock().getType() != Material.LAVA
                            || scatterLocation.getBlock().getType() != Material.STATIONARY_LAVA
                            || scatterLocation.getBlock().getType() != Material.WATER_LILY
                            || scatterLocation.getBlock().getType() != Material.WATER
                            || scatterLocation.getBlock().getType() != Material.STATIONARY_WATER
                            || !scatterLocation.getBlock().getType().isSolid()
                            || !scatterLocation.getBlock().getType().isTransparent()) {
                        valid = true;
                    }
                    ground = true;
                }
            }
        }
        scatterLocation = new Location(scatterLocation.getWorld(), scatterLocation.getX(), scatterLocation.getY()+1, scatterLocation.getZ());
        if(!scatterLocation.getChunk().isLoaded()) {
            scatterLocation.getChunk().load();
        }
        for(Player player : players) {
            player.teleport(scatterLocation);
        }
    }


    public static void scatterPlayer(World w, int r, Player p){
        List<Player> pls = new ArrayList<Player>();
        pls.add(p);
        scatter(w, r, pls);
    }

    private static Location getRandomLocation(World world, int radius){
        radius -= (radius/100);

        int x = Util.randomBetween(-radius, radius);
        int z = Util.randomBetween(-radius, radius);
        return new Location(world, x * 1.0, world.getHighestBlockYAt(x, z) + 1, z * 1.0);
    }

}
