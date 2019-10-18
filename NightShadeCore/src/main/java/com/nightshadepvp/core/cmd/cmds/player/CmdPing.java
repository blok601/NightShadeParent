package com.nightshadepvp.core.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import com.nightshadepvp.core.utils.ProxyUtil;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

/**
 * Created by Blok on 8/24/2018.
 */
public class CmdPing extends NightShadeCoreCommand {

    private static CmdPing i = new CmdPing();

    public static CmdPing get() {
        return i;
    }

    public CmdPing() {
        this.addAliases("ping", "ms");
        this.addRequirements(RequirementIsPlayer.get());
        this.addParameter(TypeNSPlayer.get(), "player", "you");
    }

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = this.readArg(NSPlayer.get(sender));
        NSPlayer senderNSPlayer = NSPlayer.get(sender);
        if (!nsPlayer.isOnline()) {
            sender.sendMessage(ChatUtils.message("&cThat player is not online!"));
            return;
        }
        int i = ((CraftPlayer) nsPlayer.getPlayer()).getHandle().ping;
        if (ProxyUtil.isBetween(20, 0, i)) {
            senderNSPlayer.msg(ChatUtils.message("&e" + nsPlayer.getName() + "'s ping is&8: &a" + i + " &8(&aExcellent&8)"));
            return;
        }

        if (ProxyUtil.isBetween(50, 20, i)) {
            senderNSPlayer.msg(ChatUtils.message("&e" + nsPlayer.getName() + "'s ping is &8: &a" + i + " &8(&aGreat&8)"));
            return;
        }

        if (ProxyUtil.isBetween(100, 50, i)) {
            senderNSPlayer.msg(ChatUtils.message("&e" + nsPlayer.getName() + "'s ping is &8: &e" + i + " &8(&eGood&8)"));
            return;
        }

        if (ProxyUtil.isBetween(150, 100, i)) {
            senderNSPlayer.msg(ChatUtils.message("&e" + nsPlayer.getName() + "'s ping is &8: &c" + i + " &8(&cPoor&8)"));
            return;
        }

        if (i > 150) {
            senderNSPlayer.msg(ChatUtils.message("&e" + nsPlayer.getName() + "'s ping is &8: &4" + i + " &8(&4Awful&8)"));
        }
    }
}
