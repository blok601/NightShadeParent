package com.nightshadepvp.core.cmd.cmds.staff.gamemode;

import com.massivecraft.massivecore.util.MUtil;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;

import java.util.List;

/**
 * Created by Blok on 8/12/2019.
 */
public class CmdGameMode extends NightShadeCoreCommand {

    private static CmdGameMode i = new CmdGameMode();

    public static CmdGameMode get() {
        return i;
    }

    public CmdGameModeCreative cmdGameModeCreative = new CmdGameModeCreative();
    public CmdGameModeSurvival cmdGameModeSurvival = new CmdGameModeSurvival();
    public CmdGameModeSpectator cmdGameModeSpectator = new CmdGameModeSpectator();
    public CmdGameModeAdventure cmdGameModeAdventure = new CmdGameModeAdventure();

    @Override
    public List<String> getAliases() {
        return MUtil.list("gamemode", "gm");
    }
}
