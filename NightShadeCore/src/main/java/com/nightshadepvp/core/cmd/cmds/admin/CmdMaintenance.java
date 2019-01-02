package com.nightshadepvp.core.cmd.cmds.admin;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanOn;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;

/**
 * Created by Blok on 1/2/2019.
 */
public class CmdMaintenance extends NightShadeCoreCommand {

    private static CmdMaintenance i = new CmdMaintenance();

    public static CmdMaintenance get() {
        return i;
    }

    public CmdMaintenance() {
        this.addAliases("maintenance");
        this.addParameter(TypeBooleanOn.get(), "Maintenance state");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.ADMIN), RequirementIsPlayer.get());
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        boolean state = this.readArg();

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(nsPlayer.getName()); // First is the name
        out.writeBoolean(state); //Second is state
        if (nsPlayer.isPlayer()) {
            nsPlayer.getPlayer().sendPluginMessage(Core.get(), "BungeeCord", out.toByteArray());
        }

        nsPlayer.msg(ChatUtils.message("&eMaintenance mode " + (state ? "&astarting..." : "&cstopped")));

    }
}
