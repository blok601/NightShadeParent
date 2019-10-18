package com.nightshadepvp.meetup.entity.object;

import org.bukkit.World;

/**
 * Created by Blok on 7/31/2019.
 */
public class Map {

    private int radius;
    private World world;

    public Map(int radius, World world) {
        this.radius = radius;
        this.world = world;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
