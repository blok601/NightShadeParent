package com.nightshadepvp.core.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import com.nightshadepvp.core.utils.ReflectionUtils;

public class CmdSeen extends NightShadeCoreCommand {

    private static CmdSeen i = new CmdSeen();
    public static CmdSeen get(){ return i;}

    public CmdSeen() {
        this.addAliases("seen");
        this.addParameter(TypeNSPlayer.get(), "target", "Player you wish to see last seen");
        this.setDesc("View when a player was last seen");
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        NSPlayer target = this.readArg();

        if(target.isOnline()){
            nsPlayer.msg(ChatUtils.message("&f" + target.getName() + " &bis &aonline"));
            return;
        }

        if(target.getLastSeen() == null){
            nsPlayer.msg(ChatUtils.message("&cLast seen data for &f" + target.getName() + " &ccan not be loaded right now!"));
            return;
        }

        long[] times = ReflectionUtils.elapsedTimeSince(target.getLastSeen());
        long seconds = times[0];
        long mins = times[1];
        long hours = times[2];
        long days = times[3];

        if(days < 1){

            if(hours < 1){
                if(mins < 1){
                    nsPlayer.msg(ChatUtils.message("&f" + target.getName() + " &bwas last seen &f" + seconds + "s &bago"));
                    return;
                }

                nsPlayer.msg(ChatUtils.message("&f" + target.getName() + " &bwas last seen &f" + mins + "m " + seconds + "s &bago"));
                return;
            }

            nsPlayer.msg(ChatUtils.message("&f" + target.getName() + " &bwas last seen &f" + hours + "h " + mins + "m " + seconds + "s &bago"));
            return;
        }

        nsPlayer.msg(ChatUtils.message("&f" + target.getName() + " &bwas last seen &f" + days + "d " + hours + "h " + mins + "m " + seconds + "s &bago"));
        return;
    }
}
