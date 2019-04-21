package me.blok601.nightshadeuhc.scenario.cmd.btc;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.scenario.BestBTCScenario;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 4/21/2019.
 */
public class BTCAddCommand implements UHCCommand {

    private ScenarioManager scenarioManager;
    private BestBTCScenario scenario;

    public BTCAddCommand(ScenarioManager scenarioManager) {
        this.scenarioManager = scenarioManager;
        if (scenarioManager.getScen("Best BTC") != null) {
            this.scenario = (BestBTCScenario) scenarioManager.getScen("Best BTC");
        }
    }

    @Override
    public String[] getNames() {
        return new String[]{
                "btcadd"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        UHCPlayer uhcPlayer = UHCPlayer.get(s);
        if (!scenario.isEnabled()) {
            uhcPlayer.msg(ChatUtils.message("&cBest BTC is not enabled!"));
            return;
        }

        if (args.length != 1) {
            scenario.sendMessage(uhcPlayer, "&cUsage: /btcadd <player>");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            scenario.sendMessage(uhcPlayer, "&cThat player couldn't be found!");
            return;
        }

        if (UHCPlayer.get(target).isSpectator()) {
            scenario.sendMessage(uhcPlayer, "&cYou can't add spectators to the BestBTC list!");
            return;
        }

        if (scenario.getList().contains(target.getUniqueId())) {
            scenario.sendMessage(uhcPlayer, "&cThat player is already on the BestBTC list!");
            return;
        }

        scenario.getList().add(target.getUniqueId());
        scenario.sendMessage(uhcPlayer, "&a" + target.getName() + " &bhas been added to the BestBTC list!");

    }

    @Override
    public boolean playerOnly() {
        return false;
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
