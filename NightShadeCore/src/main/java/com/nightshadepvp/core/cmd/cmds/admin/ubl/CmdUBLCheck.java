package com.nightshadepvp.core.cmd.cmds.admin.ubl;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.ubl.UBLEntry;
import com.nightshadepvp.core.ubl.UBLHandler;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

/**
 * Created by Blok on 12/22/2018.
 */
public class CmdUBLCheck extends NightShadeCoreCommand {


    public CmdUBLCheck() {
        this.addAliases("check", "search");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.PLAYER));
        this.addParameter(TypeString.get());
    }

    @Override
    public void perform() throws MassiveException {
        String name = this.readArg();
        NSPlayer nsPlayer = NSPlayer.get(sender);
        UBLHandler ublHandler = Core.get().getUblHandler();
        if (ublHandler.getEntry(name) != null) { //Check by name first to prevent lookup from Bukkit
            UBLEntry entry = ublHandler.getEntry(name);
            nsPlayer.msg("&eLoading &b" + name + "'s &eUBL information...");
            nsPlayer.msg(ublHandler.getTargetBanMessage(entry));
            return;
        }

        //Couldn't find by name - search from bukkit
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
        if (offlinePlayer == null) {
            nsPlayer.msg(ChatUtils.message("&cThat player couldn't be found!"));
            return;
        }

        UUID uuid = offlinePlayer.getUniqueId();
        if (ublHandler.getEntry(uuid) == null) {
            nsPlayer.msg(ChatUtils.message("&cThat player is not UBLed!"));
            return;
        }

        UBLEntry entry = ublHandler.getEntry(uuid);
        nsPlayer.msg("&eLoading &b" + name + "'s &eUBL information...");
        nsPlayer.msg(ublHandler.getTargetBanMessage(entry));
    }
}
