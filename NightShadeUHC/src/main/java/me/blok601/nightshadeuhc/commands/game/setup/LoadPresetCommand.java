package me.blok601.nightshadeuhc.commands.game.setup;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import me.blok601.nightshadeuhc.gui.PresetGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 11/11/2017.
 */
public class LoadPresetCommand implements CmdInterface{
    @Override
    public String[] getNames() {
        return new String[]{
                "loadpreset"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        new PresetGUI(p);
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.HOST;
    }

    @Override
    public boolean hasRequiredRank() {
        return true;
    }
}
