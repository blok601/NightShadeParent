package com.nightshadepvp.core.cmd.cmds.staff;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.req.ReqRankHasAtLeast;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import com.nightshadepvp.core.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CmdClear extends NightShadeCoreCommand {

    private static CmdClear i = new CmdClear();

    public static CmdClear get() {
        return i;
    }

    public CmdClear() {
        this.addAliases("clear");
        this.addParameter(TypeString.get(), "player", "you");
        this.addRequirements(ReqRankHasAtLeast.get(Rank.TRIAL));
    }

    @Override
    public void perform() throws MassiveException {
        String arg = this.readArg();

        if (arg.equalsIgnoreCase("*") || arg.equalsIgnoreCase("all")) {
            Bukkit.getOnlinePlayers().forEach(player -> player.getInventory().clear());
            NSPlayer.get(sender).msg(ChatUtils.message("&bCleared &fall &binventories!"));
            return;
        }

        Player target = Bukkit.getPlayer(arg);
        if (target == null) {
            NSPlayer.get(sender).msg(ChatUtils.message("&cThat player couldn't be found!"));
            return;
        }

        target.getInventory().clear();
        sender.sendMessage(ChatUtils.message("&bCleared the inventory of &f" + target.getName()));

    }
}
