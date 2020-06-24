package com.nightshadepvp.tournament;


import com.google.common.base.Joiner;
import com.massivecraft.massivecore.MassivePlugin;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import com.nightshadepvp.tournament.challonge.Challonge;
import com.nightshadepvp.tournament.entity.handler.ArenaHandler;
import com.nightshadepvp.tournament.entity.handler.GameHandler;
import com.nightshadepvp.tournament.entity.handler.KitHandler;
import com.nightshadepvp.tournament.entity.handler.RoundHandler;
import com.nightshadepvp.tournament.scoreboard.ScoreboardLib;
import com.nightshadepvp.tournament.task.WeatherTask;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public final class Tournament extends MassivePlugin {

    private static Tournament i;

    public Tournament() {
        Tournament.i = this;
    }

    private Location spawnLocation;
    private Location editLocation;
    private Challonge challonge;
    private Selection editLocationSelection;


    @Override
    public void onEnableInner() {
        this.activateAuto();

        getConfig().options().copyDefaults(true);
        saveConfig();

        Settings.getSettings().createFiles(this);
        ArenaHandler.getInstance().setup();
        KitHandler.getInstance().setup();
        GameHandler.getInstance().setup();
        RoundHandler.getInstance().setup();
        ScoreboardLib.setPluginInstance(this);
        loadSpawn();
        loadEdit();


        new WeatherTask(this).runTaskTimer(this, 0, 3600);
        Core.get().getLogManager().log(Logger.LogType.INFO, "Tournaments v" + this.getDescription().getVersion() + " by " + Joiner.on(", ").join(this.getDescription().getAuthors()));

    }



    @Override
    public void onDisable() {
        saveSpawn();
        saveEdit();
        ArenaHandler.getInstance().save();
        KitHandler.getInstance().save();
        Settings.getSettings().saveFiles();
        i = null;
    }

    private void loadSpawn(){

        FileConfiguration config = Settings.getSettings().getArenas();

        if (!config.contains("spawn")) {
            return;
        }

        World world = Bukkit.getWorld(config.getString("spawn.world"));
        double x = config.getDouble("spawn.x");
        double y = config.getDouble("spawn.y");
        double z = config.getDouble("spawn.z");
        if(world == null) world = Bukkit.getWorlds().get(0);
        this.spawnLocation = new Location(world, x, y, z);
    }

    private void saveSpawn(){
        FileConfiguration config = Settings.getSettings().getArenas();

        config.set("spawn.world", getSpawnLocation().getWorld().getName());
        config.set("spawn.x", getSpawnLocation().getX());
        config.set("spawn.y", getSpawnLocation().getY());
        config.set("spawn.z", getSpawnLocation().getZ());
        Settings.getSettings().saveArenas();
    }

    private void loadEdit() {
        FileConfiguration config = Settings.getSettings().getArenas();

        if (!config.contains("edit")) {
            return;
        }
        ConfigurationSection section = config.getConfigurationSection("edit");

        World world = Bukkit.getWorld(section.getString("world"));
        double x = section.getDouble("x");
        double y = section.getDouble("y");
        double z = section.getDouble("z");
        if (world == null) world = Bukkit.getWorlds().get(0);
        this.editLocation = new Location(world, x, y, z);
        Core.get().getLogManager().log(Logger.LogType.INFO, "Registered kit editing location");

        if(!section.contains("selection")) return;

        double maxx = section.getDouble("selection.max.x");
        double maxy = section.getDouble("selection.max.y");
        double maxz = section.getDouble("selection.max.z");
        double minx = section.getDouble("selection.min.x");
        double miny = section.getDouble("selection.min.y");
        double minz = section.getDouble("selection.min.z");
        Location max = new Location(world, maxx, maxy, maxz);
        Location min = new Location(world, minx, miny, minz);
        this.editLocationSelection = new CuboidSelection(world, min, max);
        Core.get().getLogManager().log(Logger.LogType.INFO, "Registered kit editing selection!");
    }

    private void saveEdit() {
        FileConfiguration config = Settings.getSettings().getArenas();

        ConfigurationSection section = config.getConfigurationSection("edit");
        section.set("world", getEditLocation().getWorld().getName());
        section.set("x", getEditLocation().getX());
        section.set("y", getEditLocation().getY());
        section.set("z", getEditLocation().getZ());

        if(this.editLocationSelection != null){
            section.set("selection.max.x", this.editLocationSelection.getMaximumPoint().getX());
            section.set("selection.max.y", this.editLocationSelection.getMaximumPoint().getY());
            section.set("selection.max.z", this.editLocationSelection.getMaximumPoint().getZ());

            section.set("selection.min.x", this.editLocationSelection.getMinimumPoint().getX());
            section.set("selection.min.y", this.editLocationSelection.getMinimumPoint().getY());
            section.set("selection.min.z", this.editLocationSelection.getMinimumPoint().getZ());
        }


        Settings.getSettings().saveArenas();
    }


    public static Tournament get() {
        return i;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public Location getEditLocation() {
        return editLocation;
    }

    public void setEditLocation(Location editLocation) {
        this.editLocation = editLocation;
    }

    public WorldEditPlugin getWorldEdit(){
        Plugin p = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        if (p instanceof WorldEditPlugin) return (WorldEditPlugin) p;
        else return null;
    }

    public Challonge getChallonge() {
        return challonge;
    }

    public void setChallonge(Challonge challonge) {
        this.challonge = challonge;
    }

    public Selection getEditLocationSelection() {
        return editLocationSelection;
    }

    public void setEditLocationSelection(Selection editLocationSelection) {
        this.editLocationSelection = editLocationSelection;
    }
}
