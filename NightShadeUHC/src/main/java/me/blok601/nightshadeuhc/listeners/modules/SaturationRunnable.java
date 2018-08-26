package me.blok601.nightshadeuhc.listeners.modules;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

/**
 * Created by Blok on 12/29/2017.
 */
public class SaturationRunnable extends BukkitRunnable{

    private UUID uuid;
    private float init;
    private double multiplier;

    public SaturationRunnable(UUID uuid, float init, double multiplier) {
        this.uuid = uuid;
        this.init = init;
        this.multiplier = multiplier;
    }

    @Override
    public void run() {
        final Player player = Bukkit.getPlayer(uuid);

        if (player == null) return;

        final float change = player.getSaturation() - init;

        player.setSaturation(init + (change * ((float) multiplier)));
    }
}
