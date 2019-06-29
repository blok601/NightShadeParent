package com.nightshadepvp.core.cmd.cmds.admin.currency;

import com.massivecraft.massivecore.util.MUtil;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;

import java.util.List;

/**
 * Created by Blok on 6/27/2019.
 */
public class CmdCoin extends NightShadeCoreCommand {

    private static CmdCoin i = new CmdCoin();

    public static CmdCoin get() {
        return i;
    }

    public CmdCoinAdd cmdCoinAdd = new CmdCoinAdd();
    public CmdCoinRemove cmdCoinRemove = new CmdCoinRemove();
    public CmdCoinSet cmdCoinSet = new CmdCoinSet();

    @Override
    public List<String> getAliases() {
        return MUtil.list("coin");

    }

}
