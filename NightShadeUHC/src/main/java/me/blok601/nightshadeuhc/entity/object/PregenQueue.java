package me.blok601.nightshadeuhc.entity.object;

import lombok.Data;
import org.bukkit.World;

import java.util.UUID;

/**
 * Created by Blok on 12/14/2018.
 */
@Data
public class PregenQueue {

    private World world;
    private boolean running;
    private int radius;
    private UUID starter;

    public PregenQueue() {
        this.running = false;
    }
}
