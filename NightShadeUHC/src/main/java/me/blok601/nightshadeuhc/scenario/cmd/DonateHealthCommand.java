package me.blok601.nightshadeuhc.scenario.cmd;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DonateHealthCommand implements UHCCommand {
    private ScenarioManager scenarioManager;

    public DonateHealthCommand(ScenarioManager scenarioManager) {
        this.scenarioManager = scenarioManager;
    }

    @Override
    public String[] getNames() {
        return new String[]{
                "explain"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;

        if (!scenarioManager.getScen("Health Donor").isEnabled()) {
            p.sendMessage(ChatUtils.message("&cHealth donor is not enabled!"));
        }
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            p.sendMessage(ChatUtils.message("&cYou must specify a valid online player!"));
        }
        if (args.length == 1) {
            p.sendMessage(ChatUtils.message("&cYou must specify an amount of health to donate!"));

        }
        try {
            int donate = Integer.parseInt(args[1]);
            if (p.getHealth() <= donate) {
                p.sendMessage(ChatUtils.message("&cYou cannot donate more health than you have!"));
            }
            if (donate < 1) {
                p.sendMessage(ChatUtils.message("&cYou cannot donate negative health!"));
            }
            double healthdiff = target.getMaxHealth() - target.getHealth();

            p.setHealth(p.getHealth() - donate);
            p.playSound(p.getLocation(), Sound.BAT_DEATH, 5, 5 );
            p.sendMessage(ChatUtils.message("&3Successfully Donated Health to " +target.getName() + "!"));


            if (target.getHealth() + (double) donate > target.getMaxHealth()) {
                double addhealth = donate - healthdiff;
                target.setMaxHealth(target.getMaxHealth() + addhealth);
                target.setHealth(target.getMaxHealth());
                target.sendMessage(ChatUtils.message("&3 Someone donated you " + donate + " health, and thus your max health has increased!!"));

            }
            else {
                target.setHealth(target.getHealth() + donate);
                target.sendMessage(ChatUtils.message("&3 Someone donated you " + donate + " health!"));

            }



        } catch (NumberFormatException e) {
            p.sendMessage(ChatUtils.message("&cAmount of health must be an integer!"));

        }
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return null;
    }

    @Override
    public boolean hasRequiredRank() {
        return false;
    }
}

