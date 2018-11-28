package me.blok601.nightshadeuhc.commands.extras;


import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements UHCCommand {


    @Override
    public String[] getNames() {
        return new String[]{
                "heal"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if (args.length != 1) {
            p.setHealth(p.getMaxHealth());
            p.sendMessage(ChatUtils.message("&eYou have been healed!"));
            return;
        }

        if (args[0].equalsIgnoreCase("*") || args[0].equalsIgnoreCase("all")) {
            Bukkit.getOnlinePlayers().stream().forEach(o -> o.setHealth(o.getMaxHealth()));
            p.sendMessage(ChatUtils.message("&eEveryone has been healed!"));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            p.sendMessage(ChatUtils.message("&cThat player couldn't be found!"));
            return;
        }

        target.setHealth(target.getMaxHealth());
        p.sendMessage(ChatUtils.message("&eHealed: &a" + target.getName()));
        return;

    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.TRIAL;
    }

    @Override
    public boolean hasRequiredRank() {
        return true;
    }
}
