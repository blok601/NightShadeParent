package com.nightshadepvp.core.cmd.cmds.staff;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import com.nightshadepvp.core.utils.PlayerUtils;
import org.bukkit.entity.Player;

public class CmdClear extends NightShadeCoreCommand {

    private static CmdClear i = new CmdClear();
    public static CmdClear get() {return i;}

    public CmdClear(){
        this.addAliases("clear");
        this.addParameter(TypePlayer.get(), "player", "you");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));
    }

    @Override
    public void perform() throws MassiveException {
        Player toClear = this.readArg(sender instanceof Player ? NSPlayer.get(sender).getPlayer() : null);
        if(toClear == null || !toClear.isOnline()){
            sender.sendMessage(ChatUtils.message("&cPlayer not found."));
            return;
        }

        toClear.getInventory().clear();
        sender.sendMessage(ChatUtils.message("&bCleared the inventory of &f" + toClear.getName()));

    }
}
