package com.nightshadepvp.meetup.entity.object;

import org.bukkit.World;

/**
 * Created by Blok on 10/15/2018.
 */
public class Map {

    private World world;
    private int radius;

    public Map(World world, int radius) {
        this.world = world;
        this.radius = radius;
    }

    public Map(World world) {
        this.world = world;
    }

    public Map(int radius) {
        this.radius = radius;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
