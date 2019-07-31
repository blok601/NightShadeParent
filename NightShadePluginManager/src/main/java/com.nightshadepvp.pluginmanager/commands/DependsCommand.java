package com.nightshadepvp.pluginmanager.commands;

import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;

/**
 * Created by Blok on 7/31/2019.
 */
public class DependsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!p.isOp()) {
                p.sendMessage(ChatColor.DARK_RED + "Invalid permissions!");
                return false;
            }

            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "Usage: /depends <plugin>");
                return false;
            }

            String pluginName = args[0];
            org.bukkit.plugin.PluginManager pluginManager = Bukkit.getPluginManager();
            Plugin plugin = pluginManager.getPlugin(pluginName);
            if (plugin == null) {
                p.sendMessage(ChatColor.RED + "Invalid plugin!");
                return false;
            }

            HashSet<Plugin> depends = Sets.newHashSet();
            for (Plugin plugins : pluginManager.getPlugins()) {
                if (plugin.getName().equalsIgnoreCase(plugins.getName())) continue;
                if (plugins.getDescription().getDepend().contains(plugin.getName()) || plugins.getDescription().getSoftDepend().contains(plugin.getName())) {
                    depends.add(plugins);
                }

                //Searched through all plugins and checked if they have the plugin as a depend, added that plugin to a list
            }

            p.sendMessage(ChatColor.DARK_PURPLE + "NightShade " + ChatColor.DARK_GRAY + "» " + ChatColor.YELLOW + "Plugins that depend on " + ChatColor.GREEN + plugin.getName() + ":");
            depends.forEach(t -> p.sendMessage(ChatColor.AQUA + t.getName() + " - " + t.getDescription().getVersion()));

        }

        return false;
    }
}
