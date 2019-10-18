package me.blok601.nightshadeuhc.entity;

import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.store.Entity;
import org.bukkit.Location;

@EditorName("config")
public class MConf extends Entity<MConf> {

    protected static transient MConf i;

    public static MConf get() {
        return i;
    }

    @Override
    public MConf load(MConf that) {
        super.load(that);
        this.setSpawnLocation(that.spawnLocation);
        this.setArenaLocation(that.arenaLocation);
        this.setKillHologramLocations(that.killHologramLocations);
        this.setWinnerHologramLocations(that.winnerHologramLocations);
        return this;
    }

    private PS spawnLocation;
    private PS arenaLocation;
    private MassiveList<PS> winnerHologramLocations;
    private MassiveList<PS> killHologramLocations;


    public void updateSpawnLocation(Location location) {
        this.spawnLocation = PS.valueOf(location);
        this.changed();
    }

    public void updateArenaLocation(Location location) {
        this.arenaLocation = PS.valueOf(location);
        this.changed();
    }

    public PS getSpawnLocation() {
        if (spawnLocation == null) {
            return PS.valueOf("world", 0, 0);
        }
        return spawnLocation;
    }

    public void setSpawnLocation(PS spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public PS getArenaLocation() {
        return arenaLocation;
    }

    public void setArenaLocation(PS arenaLocation) {
        this.arenaLocation = arenaLocation;
    }


    public void addWinner(PS ps){
        this.winnerHologramLocations.add(ps);
        this.changed();
    }

    public void removeWinner(PS ps){
        this.winnerHologramLocations.remove(ps);
        this.changed();
    }

    public void addKill(PS ps){
        this.killHologramLocations.add(ps);
        this.changed();
    }

    public void removeKill(PS ps){
        this.killHologramLocations.remove(ps);
        this.changed();
    }

    public MassiveList<PS> getWinnerHologramLocations() {
        return winnerHologramLocations;
    }

    public MassiveList<PS> getKillHologramLocations() {
        return killHologramLocations;
    }

    public void setWinnerHologramLocations(MassiveList<PS> winnerHologramLocations) {
        this.winnerHologramLocations = winnerHologramLocations;
    }

    public void setKillHologramLocations(MassiveList<PS> killHologramLocations) {
        this.killHologramLocations = killHologramLocations;
    }
}