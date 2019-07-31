package com.nightshadepvp.tournament.cmd.cmds.admin.arena;

import com.massivecraft.massivecore.util.MUtil;
import com.nightshadepvp.tournament.cmd.NightShadeTournamentCommand;

import java.util.List;

/**
 * Created by Blok on 7/18/2018.
 */
public class CmdArena extends NightShadeTournamentCommand {


    private static CmdArena i = new CmdArena();
    public static CmdArena get() {return i;}

    public CmdArenaCreate cmdArenaCreate = new CmdArenaCreate();
    public CmdArenaReload cmdArenaReload = new CmdArenaReload();
    public CmdArenaRemove cmdArenaRemove = new CmdArenaRemove();
    public CmdArenaSetSpawn1 cmdArenaSetSpawn1 = new CmdArenaSetSpawn1();
    public CmdArenaSetSpawn2 cmdArenaSetSpawn2 = new CmdArenaSetSpawn2();
    public CmdArenaTeleport cmdArenaTeleport = new CmdArenaTeleport();

    @Override
    public List<String> getAliases() {
        return MUtil.list("arena", "arenas");
    }
}
