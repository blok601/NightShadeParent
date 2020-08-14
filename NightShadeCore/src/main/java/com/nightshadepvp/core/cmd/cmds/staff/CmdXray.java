package com.nightshadepvp.core.cmd.cmds.staff;

import com.google.common.collect.Sets;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.lunar.api.LunarClientAPI;
import com.nightshadepvp.core.lunar.api.type.StaffModule;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;

public class CmdXray extends NightShadeCoreCommand {

    private HashSet<UUID> inXray = Sets.newHashSet();

    private static CmdXray i = new CmdXray();
    public static CmdXray get(){return i;}

    public CmdXray() {
        this.addAliases("xray");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL), RequirementIsPlayer.get());
        this.setDesc("Enable/Disable xray when using lunar client");
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        Player player = nsPlayer.getPlayer();
        LunarClientAPI api = Core.get().getApi();
        if(!api.isAuthenticated(player)){
            nsPlayer.msg(ChatUtils.message("&cYou must be using Lunar Client to use this command!"));
            return;
        }

        if(inXray.contains(player.getUniqueId())){
            try {
                api.toggleStaffModule(player, StaffModule.XRAY, false);
                player.sendMessage(ChatUtils.message("&fXray &bhas been &cdisabled"));
                inXray.remove(player.getUniqueId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                api.toggleStaffModule(player, StaffModule.XRAY, true);
                player.sendMessage(ChatUtils.message("&fXray &bhas been &aenabled"));
                inXray.add(player.getUniqueId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
