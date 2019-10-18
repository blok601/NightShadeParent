package me.blok601.nightshadeuhc.scenario.cmd.btc;

import com.google.common.base.Joiner;
import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.scenario.BestBTCScenario;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.HashSet;

/**
 * Created by Blok on 4/21/2019.
 */
public class BTCListCommand implements UHCCommand {

    private ScenarioManager scenarioManager;
    private BestBTCScenario scenario;

    public BTCListCommand(ScenarioManager scenarioManager) {
        this.scenarioManager = scenarioManager;
        if (scenarioManager.getScen("Best BTC") != null) {
            this.scenario = (BestBTCScenario) scenarioManager.getScen("Best BTC");
        }
    }

    @Override
    public String[] getNames() {
        return new String[]{
                "btclist"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        UHCPlayer uhcPlayer = UHCPlayer.get(s);
        if (!scenario.isEnabled()) {
            uhcPlayer.msg(ChatUtils.message("&cBest BTC is not enabled!"));
            return;
        }

        HashSet<String> names = new HashSet<>();
        scenario.getList().forEach(uuid -> names.add(UHCPlayer.get(uuid).getName()));

        scenario.sendMessage(uhcPlayer, "&ePlayers on the BTC list: &b" +

                Joiner.on("&8, &b").join(names)

        );

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
