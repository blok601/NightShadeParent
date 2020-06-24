package com.nightshadepvp.hub.command;

import com.nightshadepvp.core.entity.NSPlayer;
import com.nightshadepvp.core.utils.ChatUtils;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 2/5/2019.
 */
public class HelpCommand extends HubCommand {

    public HelpCommand() {
        super("help");

        this.setAllowConsole(true);
    }

    @Override
    public String getDescription() {
        return "Receive useful links and instructions for NightShadePvP!";
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        NSPlayer nsPlayer = NSPlayer.get(sender);
        if (nsPlayer.isPlayer()) {
            Player p = nsPlayer.getPlayer();
            p.playSound(p.getLocation(), Sound.NOTE_BASS, 5, 5);
        }

        nsPlayer.msg(ChatUtils.format("&7&m--&7&m+&7&m--------------------------------&8&m+&7&m--"));
        nsPlayer.msg(ChatUtils.format("&5Twitter: &r@NightShadePvPMC"));
        nsPlayer.msg(ChatUtils.format("&5Discord: &rdiscord.nightshadepvp.com"));
        nsPlayer.msg(ChatUtils.format("&5Store: &rwww.nightshadepvp.com/shop/"));
        nsPlayer.msg(" ");
        nsPlayer.msg(ChatUtils.format("&5Helpful Commands:"));
        nsPlayer.msg(ChatUtils.format("  → &5/helpop &r- &bSend a message to online staff members"));
        nsPlayer.msg(ChatUtils.format("  → &5/report &r- &bReport another player for breaking server rules"));
        nsPlayer.msg(ChatUtils.format("  → &5/stats &r- &bView your or someone else's stats"));
        nsPlayer.msg(ChatUtils.format("&7&m--&7&m+&7&m--------------------------------&8&m+&7&m--"));
    }
}
