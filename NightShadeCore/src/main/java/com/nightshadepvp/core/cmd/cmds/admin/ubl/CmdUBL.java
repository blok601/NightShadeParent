package com.nightshadepvp.core.cmd.cmds.admin.ubl;

import com.massivecraft.massivecore.util.MUtil;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;

import java.util.List;

/**
 * Created by Blok on 12/17/2018.
 */
public class CmdUBL extends NightShadeCoreCommand {

    private static CmdUBL i = new CmdUBL();

    public static CmdUBL get() {
        return i;
    }

    public CmdUBLExempt cmdUblExempt = new CmdUBLExempt();
    public CmdUBLUnExempt cmdUBLUnExempt = new CmdUBLUnExempt();
    public CmdUBLUpdate cmdUBLUpdate = new CmdUBLUpdate();

    @Override
    public List<String> getAliases() {
        return MUtil.list("ubl");
    }
}
