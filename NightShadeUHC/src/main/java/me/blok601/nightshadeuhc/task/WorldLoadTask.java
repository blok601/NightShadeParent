package me.blok601.nightshadeuhc.task;

import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import me.blok601.nightshadeuhc.UHC;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Blok on 3/30/2018.
 */
public class WorldLoadTask extends BukkitRunnable {

    private Runnable callback;
    private UHC instance;
    List<String> INVALID = Collections.unmodifiableList(Arrays.asList("timings", "plugins", "logs", "crash-reports"));
    private ArrayList<File> files;

    public WorldLoadTask(Runnable callback, UHC instance) {
        this.callback = callback;
        this.instance = instance;
        files = new ArrayList<>();


        for (File file : instance.getDataFolder().listFiles()) {

            if (file == null) continue;

            if (INVALID.contains(file.getName())) continue;

            if (file.isDirectory() && file.getName().toLowerCase().contains("uhcworld")) {
                files.add(file);
            }
        }
    }

    @Override
    public void run() {
        for (File file : files) {

            if (Bukkit.getWorld(file.getName()) != null) continue;

            instance.getServer().createWorld(new WorldCreator(file.getName()));
            Core.get().getLogManager().log(Logger.LogType.DEBUG, "Loaded world:" + file.getName());
        }

        callback.run();
    }
}
