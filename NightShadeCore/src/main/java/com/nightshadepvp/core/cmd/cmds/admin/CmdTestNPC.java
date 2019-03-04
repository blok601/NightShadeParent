package com.nightshadepvp.core.cmd.cmds.admin;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.objects.FakePlayer;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 3/4/2019.
 */
public class CmdTestNPC extends NightShadeCoreCommand {

    public CmdTestNPC() {
        this.addAliases("npctest");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.OWNER));
        this.setVisibility(Visibility.SECRET);
    }

    @Override
    public void perform() throws MassiveException {
        Player p = (Player) sender;
        FakePlayer fakePlayer = new FakePlayer(null, null, p.getLocation());
        fakePlayer.spawnFor(p);
        p.sendMessage("Appeared!");
    }
}
