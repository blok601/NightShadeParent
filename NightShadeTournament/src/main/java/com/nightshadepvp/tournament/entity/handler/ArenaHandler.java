package com.nightshadepvp.tournament.entity.handler;

import com.google.common.collect.Range;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import com.nightshadepvp.tournament.Settings;
import com.nightshadepvp.tournament.entity.objects.data.Arena;
import com.nightshadepvp.tournament.utils.WorldUtils;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Blok on 6/12/2018.
 */
public class ArenaHandler {

    private static ArenaHandler ourInstance = new ArenaHandler();

    public static ArenaHandler getInstance() {
        return ourInstance;
    }

    private ArenaHandler() {
    }

    private ArrayList<Arena> arenas;
    FileConfiguration configuration;

    public void setup(){
        this.arenas = new ArrayList<>();
        configuration = Settings.getSettings().getArenas();
        Arena tempArena;
        ConfigurationSection section;
        for (String arena : configuration.getKeys(false)){
            if (arena.equalsIgnoreCase("spawn") || arena.equalsIgnoreCase("edit")) continue; //Not arenas!
            tempArena = new Arena(arena);
            section = configuration.getConfigurationSection(arena);
            if(section.contains("creator")){
                tempArena.setCreator(section.getString("creator"));
            }

            if(section.contains("world")){
                if(Bukkit.getWorld(section.getString("world")) == null){
                    Core.get().getLogManager().log(Logger.LogType.SEVERE, "World for arena: " + arena + " couldn't be found! Skipping!");
                    continue;
                }

                tempArena.setWorld(Bukkit.getWorld(section.getString("world")));
            }
            double x;
            double y;
            double z;
            if(!section.contains("spawn1")){
                Core.get().getLogManager().log(Logger.LogType.SEVERE, "Arena: " + arena + " doesn't have a spawn location, skipping!");
                continue;
            }

            x = section.getDouble("spawn1.x");
            y = section.getDouble("spawn1.y");
            z = section.getDouble("spawn1.z");
            tempArena.setSpawnLocation1(new Location(tempArena.getWorld(), x, y, z));


            if(!section.contains("spawn2")){
                Core.get().getLogManager().log(Logger.LogType.SEVERE, "Arena: " + arena + " doesn't have a spawn location, skipping!");
                continue;
            }
            x = section.getDouble("spawn2.x");
            y = section.getDouble("spawn2.y");
            z = section.getDouble("spawn2.z");
            tempArena.setSpawnLocation2(new Location(tempArena.getWorld(), x, y, z));
            tempArena.setInUse(false);

            double maxx = section.getDouble("selection.max.x");
            double maxy = section.getDouble("selection.max.y");
            double maxz = section.getDouble("selection.max.z");
            double minx = section.getDouble("selection.min.x");
            double miny = section.getDouble("selection.min.y");
            double minz = section.getDouble("selection.min.z");

            Location max = new Location(tempArena.getWorld(), maxx, maxy, maxz);
            Location min = new Location(tempArena.getWorld(), minx, miny, minz);
            tempArena.setSelection(new CuboidSelection(tempArena.getWorld(), min, max));
            tempArena.setBlocks(WorldUtils.blocksFromTwoPoints(max, min).stream().map(Block::getState).collect(Collectors.toList()));
            arenas.add(tempArena);
            Core.get().getLogManager().log(Logger.LogType.INFO, "Registered arena: " + tempArena.getName() + " with " + tempArena.getBlocks().size() + " blocks");
        }
    }

    public void save(){
        //Save arenas to file
        for (Arena arena : this.arenas){
            if (arena == null) continue;
            if (arena.getName() == null) continue;
            configuration.set(arena.getName() + ".creator", arena.getCreator());
            configuration.set(arena.getName() + ".world", arena.getWorld().getName());
            configuration.set(arena.getName() + ".spawn1.x", arena.getSpawnLocation1().getX());
            configuration.set(arena.getName() + ".spawn1.y", arena.getSpawnLocation1().getY());
            configuration.set(arena.getName() + ".spawn1.z", arena.getSpawnLocation1().getZ());
            configuration.set(arena.getName() + ".spawn2.x", arena.getSpawnLocation2().getX());
            configuration.set(arena.getName() + ".spawn2.y", arena.getSpawnLocation2().getY());
            configuration.set(arena.getName() + ".spawn2.z", arena.getSpawnLocation2().getZ());

            configuration.set(arena.getName() + ".selection.max.x", arena.getSelection().getMaximumPoint().getX());
            configuration.set(arena.getName() + ".selection.max.y", arena.getSelection().getMaximumPoint().getY());
            configuration.set(arena.getName() + ".selection.max.z", arena.getSelection().getMaximumPoint().getZ());
            configuration.set(arena.getName() + ".selection.min.x", arena.getSelection().getMinimumPoint().getX());
            configuration.set(arena.getName() + ".selection.min.y", arena.getSelection().getMinimumPoint().getY());
            configuration.set(arena.getName() + ".selection.min.z", arena.getSelection().getMinimumPoint().getZ());
            Core.get().getLogManager().log(Logger.LogType.DEBUG, "Saved Arena: " + arena.getName());
        }
        Settings.getSettings().saveArenas();
    }

    public void reload(){
        save();
        this.arenas.clear();
        Arena tempArena;
        ConfigurationSection section;
        for (String arena :configuration.getKeys(false)){
            tempArena = new Arena(arena);
            section = configuration.getConfigurationSection(arena);
            if(section.contains("creator")){
                tempArena.setCreator(section.getString("creator"));
            }

            if(section.contains("world")){
                if(Bukkit.getWorld(section.getString("world")) == null){
                    Core.get().getLogManager().log(Logger.LogType.SEVERE, "World for arena: " + arena + " couldn't be found! Skipping!");
                    continue;
                }

                tempArena.setWorld(Bukkit.getWorld(section.getString("world")));
            }
            double x;
            double y;
            double z;
            if(!section.contains("spawn1")){
                Core.get().getLogManager().log(Logger.LogType.SEVERE, "Arena: " + arena + " doesn't have a spawn location, skipping!");
                continue;
            }

            x = section.getDouble("spawn1.x");
            y = section.getDouble("spawn1.y");
            z = section.getDouble("spawn1.z");
            tempArena.setSpawnLocation1(new Location(tempArena.getWorld(), x, y, z));


            if(!section.contains("spawn2")){
                Core.get().getLogManager().log(Logger.LogType.SEVERE, "Arena: " + arena + " doesn't have a spawn location, skipping!");
                continue;
            }
            x = section.getDouble("spawn2.x");
            y = section.getDouble("spawn2.y");
            z = section.getDouble("spawn2.z");
            tempArena.setSpawnLocation2(new Location(tempArena.getWorld(), x, y, z));
            tempArena.setInUse(false);

            double maxx = section.getDouble("selection.max.x");
            double maxy = section.getDouble("selection.max.y");
            double maxz = section.getDouble("selection.max.z");
            double minx = section.getDouble("selection.min.x");
            double miny = section.getDouble("selection.min.y");
            double minz = section.getDouble("selection.min.z");

            Location max = new Location(tempArena.getWorld(), maxx, maxy, maxz);
            Location min = new Location(tempArena.getWorld(), minx, miny, minz);
            tempArena.setSelection(new CuboidSelection(tempArena.getWorld(), min, max));
            tempArena.setBlocks(WorldUtils.blocksFromTwoPoints(max, min).stream().map(Block::getState).collect(Collectors.toList()));
            arenas.add(tempArena);
            Core.get().getLogManager().log(Logger.LogType.INFO, "Registered arena: " + tempArena.getName() + " with " + tempArena.getBlocks().size() + " blocks");
        }
    }

    public ArrayList<Arena> getArenas() {
        return arenas;
    }

    public Arena getArena(String name){
        for (Arena a : this.arenas) {
            if (a == null) continue;
            if (a.getName().equalsIgnoreCase(name)) {
                return a;
            }
        }

        return null;
    }

    public List<Arena> getAvailableArenas() {
        List<Arena> list = new ArrayList<>();

        for (Arena a : this.arenas) {
            if (a == null) continue;
            if (!a.isInUse()) {
                list.add(a);
            }
        }
        return list;
    }

    public boolean isArena(String name){
        return getArena(name) != null;
    }

    public void removeArena(Arena arena){
        configuration.set(arena.getName(), null);
        Settings.getSettings().saveArenas();
        this.arenas.remove(arena);
    }

    public Arena getFromBlock(Block block) {
        for (Arena arena : arenas) {
            if (arena.getSelection().contains(block.getLocation())) {
                return arena;
            }
        }

        return null;
    }
}
