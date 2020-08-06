package com.nightshadepvp.hub;

import com.google.common.collect.Lists;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import com.nightshadepvp.hub.command.CommandHandler;
import com.nightshadepvp.hub.command.HubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Hub extends JavaPlugin {

    private CommandHandler commandHandler;

    @Override
    public void onEnable() {
        this.commandHandler = new CommandHandler(this);
        this.commandHandler.setup();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (this.commandHandler == null) {
            sender.sendMessage("Hub is not currently accepting commands! Please wait a few moments and try again!");
            return false;
        }

        for (HubCommand hubCommand : this.commandHandler.getCommands()) {
            List<String> possibleNames = Lists.newArrayList();
            possibleNames.add(hubCommand.getLabel());
            possibleNames.addAll(hubCommand.getAliases());

            //Got all possible names
            for (String cmdName : possibleNames) {
                if (command.getName().equalsIgnoreCase(cmdName)) {

                    if (!hubCommand.isAllowConsole()) {
                        if (!(sender instanceof Player)) {
                            sender.sendMessage(ChatUtils.message("&cThis is a player only command!"));
                            return false;
                        }
                    }

                    if (sender instanceof ConsoleCommandSender && hubCommand.isAllowConsole()) {
                        try {
                            hubCommand.run(sender, args);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }

                    Player p = (Player) sender;
                    NSPlayer user = NSPlayer.get(p.getUniqueId());
                    if (!(user.hasRank(hubCommand.getRequiredRank()))) {
                        p.sendMessage(com.nightshadepvp.core.utils.ChatUtils.message("&cYou require the " + hubCommand.getRequiredRank().getPrefix() + "&crank to do this command!"));
                        return false;
                    }

                    try {
                        hubCommand.run(sender, args);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return true;

                }
            }
        }

        return false;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }
}
