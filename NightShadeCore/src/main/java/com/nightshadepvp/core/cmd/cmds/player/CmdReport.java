package com.nightshadepvp.core.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.cmd.type.TypeNSPlayer;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.NSPlayerColl;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.UUID;

/**
 * Created by Blok on 11/19/2018.
 */
public class CmdReport extends NightShadeCoreCommand {

    public static CmdReport i = new CmdReport();

    public static CmdReport get() {
        return i;
    }

    public CmdReport() {
        this.addAliases("report");
        this.addParameter(TypeNSPlayer.get(), "player");
        this.addParameter(TypeString.get(), "reason", true);
    }

    HashSet<UUID> reportCooldown = new HashSet<>();

    @Override
    public void perform() throws MassiveException {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        if (reportCooldown.contains(nsPlayer.getUuid())) {
            nsPlayer.msg(ChatUtils.message("&cYou have recently reported someone! You must wait &e2 &cminutes in between reports!"));
            return;
        }

        NSPlayer targetNSPlayer = this.readArg();
        String reason = this.readArg();
        NSPlayerColl.get().getAllOnline().stream().filter(nsPlayer1 -> nsPlayer1.hasRank(Rank.TRIAL)).forEach(nsPlayer1 -> {
            nsPlayer1.getPlayer().playSound(nsPlayer1.getPlayer().getLocation(), Sound.NOTE_PLING, 5F, 5F);
            nsPlayer1.msg(ChatUtils.format("&7&m--&8&m+&7&m--------------------------------&8&m+&7&m--"));
            nsPlayer1.msg("&7 ► New Report from &5" + nsPlayer.getName());
            nsPlayer1.msg("&7 ► Reported&8: &5" + targetNSPlayer.getName());
            nsPlayer1.msg("&7 ► Reason&8: &5" + reason);
            nsPlayer1.msg(ChatUtils.format("&7&m--&8&m+&7&m--------------------------------&8&m+&7&m--"));
        });
        reportCooldown.add(nsPlayer.getUuid());
        new BukkitRunnable() {
            @Override
            public void run() {
                reportCooldown.remove(nsPlayer.getUuid());
            }
        }.runTaskLater(Core.get(), 20 * 60 * 2);
    }
}
