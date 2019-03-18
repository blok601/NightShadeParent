package me.blok601.nightshadeuhc.scenario.cmd.SlaveMarket;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.scenario.SlaveMarketScenario;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BidCommand implements UHCCommand {
    public static int time = 5;

    @Override
    public String[] getNames() {
        return new String[]{
                "bid"
        };
    }

    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        UHCPlayer uhcPlayer = UHCPlayer.get(p);
        if (!SlaveMarketScenario.owners.containsKey(p.getName())) {
            p.sendMessage(ChatUtils.message("&cOnly slave owners can do that command!"));
            return;
        }
        int bid = 0;
        if (args.length == 1) {
            if (StartBiddingCommand.currentBid == 0) {
                SlaveOwnerCommand.setTopBidder(p);
                ChatUtils.sendAll("&e" + p.getName() + " has bid " + bid + "diamonds on " + SlaveOwnerCommand.getSlave());

                return;
            }
            bid = StartBiddingCommand.currentBid + 1;
            SlaveOwnerCommand.setTopBidder(p);
            ChatUtils.sendAll("&e" + p.getName() + " has bid " + bid + "diamonds on " + SlaveOwnerCommand.getSlave());
            return;

        }
        if (args.length == 2) {
            if (Integer.parseInt(args[1]) > StartBiddingCommand.currentBid) {
                bid = Integer.parseInt(args[1]);
                SlaveOwnerCommand.setTopBidder(p);
                ChatUtils.sendAll("&e" + p.getName() + " has bid " + bid + "diamonds on " + SlaveOwnerCommand.getSlave());

                return;
            } else {
                p.sendMessage(ChatUtils.message("&cYou cant bid any lower than the current bid!"));
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