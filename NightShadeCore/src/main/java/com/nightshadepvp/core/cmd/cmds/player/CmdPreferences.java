package com.nightshadepvp.core.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.gui.guis.PrefrencesGUI;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 7/6/2018.
 */
public class CmdPreferences extends NightShadeCoreCommand{

    private static CmdPreferences i = new CmdPreferences();
    public static CmdPreferences get() { return i; }

    public CmdPreferences() {
        this.setAliases("preferences", "prefs");
        this.addRequirements(RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        Player p = (Player) sender;

        new PrefrencesGUI(p);
    }
}
