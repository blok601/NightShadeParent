package me.blok601.nightshadeuhc.scenario.cmd.slavemarket;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.utils.PacketUtils;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.scenario.SlaveMarketScenario;
import me.blok601.nightshadeuhc.util.ChatUtils;
import me.blok601.nightshadeuhc.util.MathUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BidCommand implements UHCCommand {

    private SlaveMarketScenario slaveMarketScenario;

    public BidCommand(SlaveMarketScenario slaveMarketScenario) {
        this.slaveMarketScenario = slaveMarketScenario;
    }

    public static int time = 5;
    @Override
    public String[] getNames() {
        return new String[]{
                "bid"
        };
    }

    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        int currentBid = 0;
        if (!slaveMarketScenario.owners.containsKey(p.getUniqueId())) {
            p.sendMessage(ChatUtils.format(slaveMarketScenario.getPrefix() + "&cOnly slave owners can do that command!"));
            return;
        }
        if (args.length == 1) {
            if (slaveMarketScenario.CURRENT_BID == 0) {
                slaveMarketScenario.CURRENT_TOP_BIDDER = p.getUniqueId();
                ChatUtils.sendAll("&e" + p.getName() + " has bid " + currentBid + "diamonds on " + PacketUtils.getNameFromUUID(slaveMarketScenario.CURRENT_SLAVE));
                return;
            }
            currentBid = slaveMarketScenario.CURRENT_BID + 1;
            slaveMarketScenario.CURRENT_TOP_BIDDER = p.getUniqueId();
            ChatUtils.sendAllNoPrefix(slaveMarketScenario.getPrefix() + "&e" + p.getName() + " has bid " + currentBid + "diamonds on " + PacketUtils.getNameFromUUID(slaveMarketScenario.CURRENT_SLAVE));
            return;

        }
        if (args.length == 2) {
            if (!MathUtils.isInt(args[1])) {
                slaveMarketScenario.sendMessage(p, "&cYou must supply a valid number!");
                return;
            }
            currentBid = Integer.parseInt(args[1]);
            if (currentBid > slaveMarketScenario.CURRENT_BID) {
                slaveMarketScenario.CURRENT_TOP_BIDDER = p.getUniqueId();
                ChatUtils.sendAllScenarioMessage("&e" + p.getName() + " has bid " + currentBid + "diamonds on " + PacketUtils.getNameFromUUID(slaveMarketScenario.CURRENT_TOP_BIDDER), slaveMarketScenario);
                return;
            } else {
                slaveMarketScenario.sendMessage(p, "&cYou cant bid lower than the current bid!");
                return;
            }
        }
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.PLAYER;
    }

    @Override
    public boolean hasRequiredRank() {
        return false;
    }
}