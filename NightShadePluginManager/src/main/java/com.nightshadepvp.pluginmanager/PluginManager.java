package com.nightshadepvp.pluginmanager;


import com.nightshadepvp.pluginmanager.commnads.VersionsCommand;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * Created by Blok on 8/26/2018.
 */
public class PluginManager extends JavaPlugin {

    private static PluginManager i;

    @Override
    public void onEnable() {
        i = this;
        getCommand("versions").setExecutor(new VersionsCommand());
    }

    @Override
    public void onDisable() {
        if(transferFiles()){
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Success" + ChatColor.DARK_GRAY + "] " + ChatColor.DARK_RED + "Transferred all files successfully!");
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "Severe" + ChatColor.DARK_GRAY + "] " + ChatColor.DARK_RED + "Couldn't copy files into plugin directory!");
        }
    }

    public static PluginManager get() {
        return i;
    }

    private boolean transferFiles() {
        File pluginsDir = new File(this.getServer().getWorldContainer() + "/plugins/");
        File templateDir = new File("/home/dhillon/minecraft/template/");
        if (!pluginsDir.isDirectory()) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "Severe" + ChatColor.DARK_GRAY + "] " + ChatColor.DARK_RED + "Couldn't properly locate plugins directory!");
            return false;
        }

        if (!templateDir.isDirectory() || templateDir.listFiles() == null) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "Severe" + ChatColor.DARK_GRAY + "] " + ChatColor.DARK_RED + "Couldn't properly locate template directory!");
            return false;
        }

        for (File file : templateDir.listFiles()) {
            if (file == null || !file.exists()) {
                continue;
            }

            try {
                FileUtils.copyFileToDirectory(file, pluginsDir);
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Success" + ChatColor.DARK_GRAY + "] " + ChatColor.DARK_RED + "Copied " + file.getName() + " successfully!");
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "Severe" + ChatColor.DARK_GRAY + "] " + ChatColor.DARK_RED + "IO Exception!");
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }
}
