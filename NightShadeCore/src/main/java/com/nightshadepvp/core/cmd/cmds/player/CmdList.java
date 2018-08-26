package com.nightshadepvp.core.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.NSPlayerColl;
import com.nightshadepvp.core.utils.ChatUtils;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Blok on 8/7/2018.
 */
public class CmdList extends NightShadeCoreCommand {

    private static CmdList i = new CmdList();

    public static CmdList get() {
        return i;
    }

    public CmdList() {
        this.addAliases("list");
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        ArrayList<NSPlayer> nsPlayers = new ArrayList<>();
        NSPlayerColl.get().getAllOnline().stream().sorted(Comparator.comparing(nsPlayer1 -> nsPlayer.getRank().getValue())).forEach(nsPlayers::add);
        //Sorted them
        StringBuilder stringBuilder = new StringBuilder();
        for (NSPlayer n : nsPlayers){
            stringBuilder.append(ChatUtils.format((n.getRank().getNameColor()))).append(ChatUtils.format(n.getName())).append(ChatUtils.format(", &r"));
        }

        String players = stringBuilder.toString().trim();
        nsPlayer.msg(ChatUtils.message("&ePlayers online:"));
        nsPlayer.msg(players.substring(0, players.length()-1));
    }
}
