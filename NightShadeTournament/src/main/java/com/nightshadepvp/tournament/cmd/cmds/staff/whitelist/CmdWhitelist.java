package com.nightshadepvp.tournament.cmd.cmds.staff.whitelist;

import com.massivecraft.massivecore.util.MUtil;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;

import java.util.List;

/**
 * Created by Blok on 8/8/2018.
 */
public class CmdWhitelist extends NightShadeTournamentCommand {

    private static CmdWhitelist i = new CmdWhitelist();

    public static CmdWhitelist get() {
        return i;
    }

    public CmdWhitelistAdd cmdWhitelistAdd = new CmdWhitelistAdd();
    public CmdWhitelistRemove cmdWhitelistRemove = new CmdWhitelistRemove();
    public CmdWhitelistClear cmdWhitelistClear = new CmdWhitelistClear();
    public CmdWhitelistList cmdWhitelistList = new CmdWhitelistList();
    public CmdWhitelistOn cmdWhitelistOn = new CmdWhitelistOn();
    public CmdWhitelistOff cmdWhitelistOff = new CmdWhitelistOff();

    @Override
    public List<String> getAliases() {
        return MUtil.list("whitelist", "wl");
    }
}
