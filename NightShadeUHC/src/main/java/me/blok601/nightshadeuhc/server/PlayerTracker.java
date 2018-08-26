package me.blok601.nightshadeuhc.server;

import me.blok601.nightshadeuhc.UHC;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


/**
 * Created by Blok on 8/4/2017.
 */
public class PlayerTracker {

    private Player player;
    private String country;
    private IPInfo.IPData data;

    public PlayerTracker(Player player) {
        this.player = player;
    }

    public String getCountry() {
        return this.country;
    }

    public void setupCountry(){
        new BukkitRunnable(){
            @Override
            public void run() {
                setData(IPInfo.getIPData(player.getAddress().getAddress().getHostAddress()));
                setCountry(getData().getCountryName());
            }
        }.runTaskAsynchronously(UHC.get());
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Player getPlayer() {
        return player;
    }

    public IPInfo.IPData getData() {
        return data;
    }

    public void setData(IPInfo.IPData data) {
        this.data = data;
    }
}
