package com.nightshadepvp.tournament;

import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * Created by Blok on 7/1/2017.
 */
public class Settings {

    public static Settings getSettings() {
        return new Settings();
    }

    static FileConfiguration config;
    static File cFile;

    static FileConfiguration kits;
    static File kFile;

    static FileConfiguration arenas;
    static File aFile;


    public void createFiles(Plugin p) {
        if (!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }
        cFile = new File(p.getDataFolder(), "config.yml");
        if (!cFile.exists()) {
            Bukkit.getServer().getLogger()
                    .info("NS: Config.yml not found, creating!");
            try {
                cFile.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getLogger()
                        .severe("NS: Config.yml creation failed!");
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(cFile);

        kFile = new File(p.getDataFolder(), "kits.yml");
        if (!kFile.exists()) {
            Bukkit.getServer().getLogger()
                    .info("NS: Kits.yml not found, creating!");
            try {
                kFile.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getLogger()
                        .severe("NS: kits.yml creation failed!");
                e.printStackTrace();
            }
        }

        kits = YamlConfiguration.loadConfiguration(kFile);

        aFile = new File(p.getDataFolder(), "arenas.yml");
        if (!aFile.exists()) {
            Bukkit.getServer().getLogger()
                    .info("NS: arenas.yml not found, creating!");
            try {
                aFile.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getLogger()
                        .severe("NS: arenas.yml creation failed!");
                e.printStackTrace();
            }
        }

        arenas = YamlConfiguration.loadConfiguration(aFile);
        saveFiles();


    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(cFile);
        } catch (IOException e) {
            Bukkit.getServer().getLogger()
                    .severe(ChatColor.RED + "NS: Error saving config.yml!");
            e.printStackTrace();
        }
    }

    public void saveArenas() {
        try {
            arenas.save(aFile);
        } catch (IOException e) {
            Bukkit.getServer().getLogger()
                    .severe(ChatColor.RED + "NS: Error saving arenas.yml!");
            e.printStackTrace();
        }
    }

    public void saveKitsFile() {
        try {
            kits.save(kFile);
        } catch (IOException e) {
            Bukkit.getServer().getLogger()
                    .severe(ChatColor.RED + "NS: Error saving kits.yml!");
            e.printStackTrace();
        }
    }



    public void saveFiles() {
        saveConfig();
        saveArenas();
        saveKitsFile();
    }



    public  void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(cFile);
    }

    public void reloadKitsFile() {
        kits = YamlConfiguration.loadConfiguration(kFile);
    }

    public void reloadArenas() {
        arenas = YamlConfiguration.loadConfiguration(aFile);
    }


    public void reloadFiles() {
        reloadConfig();
        reloadArenas();
        reloadKitsFile();
    }

    public FileConfiguration getKitsFile() {
        return kits;
    }

    public FileConfiguration getArenas() {
        return arenas;
    }

    public FileConfiguration getPlayerFile(Player p, Tournament plugin){
        File file = new File(plugin.getDataFolder() + File.separator + "player", p.getUniqueId().toString() + ".yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    public boolean playerFileExists(Player p, Tournament plugin){
        File file = new File(plugin.getDataFolder() + File.separator + "player", p.getUniqueId().toString() + ".yml");
        return file.exists();
    }

    public void savePlayerFile(Player p, Tournament plugin){
        try{
            FileConfiguration configuration = getPlayerFile(p, Tournament.get());
            File file = new File(plugin.getDataFolder() + File.separator + "player", p.getUniqueId().toString() + ".yml");
            configuration.save(file);
            System.out.println("File name: " + file.getName() + " File Path: " + file.getAbsolutePath());
        }catch (IOException e){
            e.printStackTrace();
            Core.get().getLogManager().log(Logger.LogType.SEVERE, "There was a problem saving a player file (" + p.getName() + ") UUID= " + p.getUniqueId().toString());
        }
    }

    public void createPlayerFile(Player p, Tournament plugin) throws IOException{
        File file = new File(plugin.getDataFolder() + File.separator + "player", p.getUniqueId().toString() + ".yml");
        if(!file.exists()){
            file.createNewFile();
        }
        System.out.println("Creating new file for " + p.getName());
        savePlayerFile(p, plugin);
    }
}
