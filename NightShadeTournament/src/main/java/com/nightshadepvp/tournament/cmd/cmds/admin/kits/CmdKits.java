package com.nightshadepvp.tournament.cmd.cmds.admin.kits;

import com.massivecraft.massivecore.util.MUtil;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;

import java.util.List;

/**
 * Created by Blok on 7/18/2018.
 */
public class CmdKits extends NightShadeTournamentCommand {


    private static CmdKits i = new CmdKits();
    public static CmdKits get() {return i;}

    public CmdKitCreate cmdKitCreate = new CmdKitCreate();
    public CmdKitRemove cmdKitRemove = new CmdKitRemove();
    public CmdSetInv cmdSetInv = new CmdSetInv();
    public CmdKitsReload cmdKitsReload = new CmdKitsReload();
    public CmdKitList cmdKitList = new CmdKitList();
    public CmdKitSetBuild cmdKitSetBuild = new CmdKitSetBuild();
    public CmdKitSetRegen cmdKitSetRegen = new CmdKitSetRegen();
    public CmdSetDisplay cmdSetDisplay = new CmdSetDisplay();

    @Override
    public List<String> getAliases() {
        return MUtil.list("kits", "kit");
    }
}
