package com.nightshadepvp.core.cmd.cmds.server;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsntPlayer;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

/**
 * Created by Blok on 7/6/2018.
 */
public class CmdAcBan extends NightShadeCoreCommand{

    private static CmdAcBan i = new CmdAcBan();
    public static CmdAcBan get() {return i;}

    public CmdAcBan() {
        this.addAliases("acban");
        this.addParameter(TypeNSPlayer.get(), "player");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.OWNER)); // Just to be safe
        this.addRequirements(RequirementIsntPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {

        NSPlayer nsPlayer = this.readArg();

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + nsPlayer.getName() + "14d -s");
        Bukkit.getOnlinePlayers().forEach(o -> o.playSound(o.getLocation(), Sound.AMBIENCE_THUNDER, 5.0F, 5.0F));
        Bukkit.broadcastMessage(ChatUtils.format("&3NightCheat has claimed another victim"));

    }
}
