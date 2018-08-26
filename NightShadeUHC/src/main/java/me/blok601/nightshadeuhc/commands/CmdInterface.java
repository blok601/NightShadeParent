package me.blok601.nightshadeuhc.commands;

import com.nightshadepvp.core.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Blok on 6/26/2017.
 */
public interface CmdInterface {

    public String[] getNames();
    public void onCommand(CommandSender s, Command cmd, String l, String[] args);
    public boolean playerOnly();
    public Rank getRequiredRank();
    public boolean hasRequiredRank();

}
