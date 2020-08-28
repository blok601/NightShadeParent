package com.nightshadepvp.core.cmd.cmds.staff.notes;

import com.massivecraft.massivecore.util.MUtil;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;

import java.util.List;

public class CmdNote extends NightShadeCoreCommand {

    private static CmdNote i = new CmdNote();
    public static CmdNote get(){return i;}

    public CmdNoteAdd cmdNoteAdd = new CmdNoteAdd();
    public CmdNoteRemove cmdNoteRemove = new CmdNoteRemove();
    public CmdNoteClear cmdNoteClear = new CmdNoteClear();

    @Override
    public List<String> getAliases() {
        return MUtil.list("note");
    }

}
