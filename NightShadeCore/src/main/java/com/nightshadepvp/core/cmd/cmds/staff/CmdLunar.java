package com.nightshadepvp.core.cmd.cmds.staff;

import com.google.common.base.Joiner;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.lunar.api.LunarClientAPI;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class CmdLunar extends NightShadeCoreCommand {

    private static CmdLunar i = new CmdLunar();
    public static CmdLunar get() {return i;}

    public CmdLunar() {
        this.addAliases("lunar");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL), RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        LunarClientAPI api = Core.get().getApi();
        nsPlayer.msg(ChatUtils.message("&bPlayers using Lunar Client: " + Joiner.on("&7, &f").join(Bukkit.getOnlinePlayers().stream().filter(api::isAuthenticated).map(HumanEntity::getName).collect(Collectors.toList()))));
    }
}
