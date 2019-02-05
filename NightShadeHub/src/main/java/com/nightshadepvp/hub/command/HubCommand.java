package com.nightshadepvp.hub.command;

import com.google.common.collect.Lists;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.hub.Hub;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by Blok on 2/2/2019.
 */
public abstract class HubCommand {

    private String label; //Name of command
    private Rank requiredRank; //Needed rank
    private String usage; //Args
    private boolean allowConsole;

    protected Hub plugin;

    public HubCommand(String label, Rank requiredRank, String usage) {
        this.label = label;
        this.requiredRank = requiredRank;
        this.usage = usage;
    }

    public HubCommand(String label, Rank requiredRank) {
        this(label, requiredRank, "");
    }

    public HubCommand(String label, String usage) {
        this(label, Rank.PLAYER, usage);
    }

    public HubCommand(String label) {
        this(label, "");
    }

    public String getLabel() {
        return label;
    }

    public Rank getRequiredRank() {
        return requiredRank;
    }

    public String getUsage() {
        return usage;
    }

    public abstract String getDescription();

    private final List<String> aliases = Lists.newArrayList();

    public List<String> getAliases() {
        return aliases;
    }

    private CommandSender sender;
    private String[] args;

    public boolean isAllowConsole() {
        return allowConsole;
    }

    public void setAllowConsole(boolean allowConsole) {
        this.allowConsole = allowConsole;
    }

    protected boolean requireRank(CommandSender sender, Rank rank) {
        return NSPlayer.get(sender).hasRank(rank);
    }

    public abstract void run(CommandSender sender, String[] args);

    void setup(Hub input) {
        this.plugin = input;
    }
}
