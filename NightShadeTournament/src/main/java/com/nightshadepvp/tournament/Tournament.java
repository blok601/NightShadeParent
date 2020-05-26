package com.nightshadepvp.tournament;


import com.massivecraft.massivecore.MassivePlugin;
import com.nightshadepvp.tournament.challonge.Challonge;
import com.nightshadepvp.tournament.entity.handler.ArenaHandler;
import com.nightshadepvp.tournament.entity.handler.GameHandler;
import com.nightshadepvp.tournament.entity.handler.KitHandler;
import com.nightshadepvp.tournament.entity.handler.RoundHandler;
import com.nightshadepvp.tournament.scoreboard.ScoreboardLib;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
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

        World world = Bukkit.getWorld(config.getString("edit.world"));
        double x = config.getDouble("edit.x");
        double y = config.getDouble("edit.y");
        double z = config.getDouble("edit.z");
        if (world == null) world = Bukkit.getWorlds().get(0);
        this.editLocation = new Location(world, x, y, z);
    }

    private void saveEdit() {
        FileConfiguration config = Settings.getSettings().getArenas();

        config.set("edit.world", getEditLocation().getWorld().getName());
        config.set("edit.x", getEditLocation().getX());
        config.set("edit.y", getEditLocation().getY());
        config.set("edit.z", getEditLocation().getZ());
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
}
