package com.nightshadepvp.tournament.entity.objects.data;

import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Created by Blok on 6/12/2018.
 */
public class Arena {

    private String name;
    private Location spawnLocation1;
    private Location spawnLocation2;
    private String creator;
    private World world;
    private boolean inUse;
    private Selection selection;


    public Arena(String name) {
        this.name = name;
    }

    public Arena() {
        this.inUse = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getSpawnLocation1() {
        return spawnLocation1;
    }

    public void setSpawnLocation1(Location spawnLocation1) {
        this.spawnLocation1 = spawnLocation1;
    }

    public Location getSpawnLocation2() {
        return spawnLocation2;
    }

    public void setSpawnLocation2(Location spawnLocation2) {
        this.spawnLocation2 = spawnLocation2;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    public Selection getSelection() {
        return selection;
    }

    public void setSelection(Selection selection) {
        this.selection = selection;
    }
}
