package com.nightshadepvp.pluginmanager.commnads;

import com.nightshadepvp.pluginmanager.PluginManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Created by Blok on 8/26/2018.
 */
public class VersionsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.isOp()){
                p.sendMessage(ChatColor.GREEN + "Loading plugin versions...");
                p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.STRIKETHROUGH + "-------------------");
                for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()){
                    if(plugin.getName().equalsIgnoreCase("NightShadeUHC") || plugin.getName().equalsIgnoreCase("Core") || plugin.getName().equalsIgnoreCase(PluginManager.get().getName())){
                        p.sendMessage(ChatColor.YELLOW + "- " + ChatColor.DARK_PURPLE + plugin.getName());
                        continue;
                    }

                    p.sendMessage(ChatColor.YELLOW + "- " + ChatColor.DARK_AQUA + plugin.getName());
                }
                p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.STRIKETHROUGH + "-------------------");
                return true;
            }else{
                p.sendMessage(ChatColor.RED + "You don't have permission!");
                return false;
            }
        }else{
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Loading plugin versions...");
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.STRIKETHROUGH + "-------------------");
            for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()){
                if(plugin.getName().equalsIgnoreCase("NightShadeUHC") || plugin.getName().equalsIgnoreCase("Core") || plugin.getName().equalsIgnoreCase(PluginManager.get().getName())){
                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "- " + ChatColor.DARK_PURPLE + plugin.getName());
                    continue;
                }

                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "- " + ChatColor.DARK_AQUA + plugin.getName());
            }
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.STRIKETHROUGH + "-------------------");
            return true;
        }
    }
}
