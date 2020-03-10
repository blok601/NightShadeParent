package com.nightshadepvp.core.cmd.cmds.player.friend;

import com.massivecraft.massivecore.util.MUtil;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;

import java.util.List;

public class CmdFriend extends NightShadeCoreCommand {

    private static CmdFriend i = new CmdFriend();
    public static CmdFriend get() {return i;}

    public CmdFriendAdd cmdFriendAdd = new CmdFriendAdd();
    public CmdFriendRemove cmdFriendRemove = new CmdFriendRemove();

    @Override
    public List<String> getAliases() {
        return MUtil.list("friend");
    }
}
