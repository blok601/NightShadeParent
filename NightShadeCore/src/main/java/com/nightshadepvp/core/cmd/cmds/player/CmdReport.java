package com.nightshadepvp.core.cmd.cmds.player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import com.nightshadepvp.core.Core;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.cmd.NightShadeCoreCommand;
import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.entity.NSPlayerColl;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
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
        this.addParameter(TypePlayer.get(), "player");
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

        Player target = this.readArg();
        if(target == null || !target.isOnline()){
            nsPlayer.msg(ChatUtils.message("&cThat player couldn't be found!"));
            return;
        }
        String reason = this.readArg();
        NSPlayerColl.get().getAllOnline().stream().filter(nsPlayer1 -> nsPlayer1.hasRank(Rank.TRIAL)).forEach(nsPlayer1 -> {
            Player player = nsPlayer1.getPlayer();
            player.playSound(player.getLocation(), Sound.NOTE_BASS, 5, 5);
            nsPlayer1.msg(ChatUtils.format("&7&m--&8&m+&7&m--------------------------------&8&m+&7&m--"));
            nsPlayer1.msg("&7 ► New Report from &5" + nsPlayer.getName());
            nsPlayer1.msg("&7 ► Reported&8: &5" + target.getName());
            nsPlayer1.msg("&7 ► Reason&8: &5" + reason);
            nsPlayer1.msg(ChatUtils.format("&7&m--&8&m+&7&m--------------------------------&8&m+&7&m--"));
        });
        nsPlayer.msg(ChatUtils.message("&aThank you for your report. It will be handled by the moderators shortly."));
        reportCooldown.add(nsPlayer.getUuid());
        new BukkitRunnable() {
            @Override
            public void run() {
                reportCooldown.remove(nsPlayer.getUuid());
            }
        }.runTaskLater(Core.get(), 20 * 60 * 2);
    }
}
