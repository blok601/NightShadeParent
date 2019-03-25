package me.blok601.nightshadeuhc.scenario.cmd.SlaveMarket;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.utils.PacketUtils;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.entity.object.PlayerStatus;
import me.blok601.nightshadeuhc.entity.object.Team;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.scenario.SlaveMarketScenario;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;


public class StartBiddingCommand implements UHCCommand {

    private SlaveMarketScenario slaveMarketScenario;
    private UHC uhc;


    public StartBiddingCommand(SlaveMarketScenario slaveMarketScenario, UHC uhc) {
        this.slaveMarketScenario = slaveMarketScenario;
        this.uhc = uhc;
    }

    @Override
    public String[] getNames() {
        return new String[]{
                "startbid"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        UHCPlayer uhcPlayer = UHCPlayer.get(p);
        if (!uhcPlayer.isSpectator()) {
            slaveMarketScenario.sendMessage(p, "&cOnly the host can do that command!");
            return;
        }

        if (!GameManager.get().getHost().getName().equalsIgnoreCase(p.getName())) {
            slaveMarketScenario.sendMessage(p, "&cOnly the host can do that command!");
            return;
        }
        slaveMarketScenario.owners.forEach((k, v) -> {

            Player o = Bukkit.getPlayer(k);
            if (o != null) {
                o.getInventory().addItem(new ItemStack(Material.DIAMOND, v));
            }
        });

        UHCPlayerColl.get().getAllOnlinePlayers().stream().filter(uhcPlayer1 -> !uhcPlayer1.isSpectator()).forEach(uhcPlayer1 -> uhcPlayer1.setPlayerStatus(PlayerStatus.PLAYING));

        ArrayList<UHCPlayer> slavesLeft = new ArrayList<>(UHCPlayerColl.get().getAllPlaying());
        slaveMarketScenario.canBid = true;

        ChatUtils.sendAllScenarioMessage("&a" + slavesLeft.get(0).getName() + " &bcan now being bid on!", slaveMarketScenario);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (slavesLeft.size() == 0) {
                    //TODO: Finished
                    slaveMarketScenario.TIME_TO_BID = -10;
                    cancel();
                    return;
                }

                slaveMarketScenario.CURRENT_SLAVE = slavesLeft.get(slaveMarketScenario.PLAYER_INDEX).getUuid(); //TODO: Flip to next slave
                if (slaveMarketScenario.TIME_TO_BID == 0) {
                    if (slaveMarketScenario.CURRENT_TOP_BIDDER != null) {

                        String slaveName = PacketUtils.getNameFromUUID(slaveMarketScenario.CURRENT_SLAVE);

                        Team targetTeam = TeamManager.getInstance().getTeamByPlayerUUIDOnTeam(slaveMarketScenario.CURRENT_TOP_BIDDER);
                        targetTeam.addMember(PacketUtils.getNameFromUUID(slaveMarketScenario.CURRENT_SLAVE));
                        ChatUtils.sendAllScenarioMessage("&c" + slaveName + " has been sold to " + PacketUtils.getNameFromUUID(slaveMarketScenario.CURRENT_TOP_BIDDER) + "!", slaveMarketScenario);
                        int diamonds = slaveMarketScenario.owners.get(slaveMarketScenario.CURRENT_TOP_BIDDER);
                        slaveMarketScenario.owners.replace(slaveMarketScenario.CURRENT_TOP_BIDDER, diamonds - slaveMarketScenario.CURRENT_BID);

                        Player owner = Bukkit.getPlayer(slaveMarketScenario.CURRENT_TOP_BIDDER);
                        if (owner != null) {
                            owner.getInventory().removeItem(new ItemStack(Material.DIAMOND, slaveMarketScenario.CURRENT_BID));
                        }

                        slavesLeft.remove(UHCPlayer.get(slaveMarketScenario.CURRENT_SLAVE));
                        slaveMarketScenario.PLAYER_INDEX++;

                        //Flip to next guy
                        slaveMarketScenario.CURRENT_SLAVE = (slavesLeft.get(slaveMarketScenario.PLAYER_INDEX)).getUuid();
                        slaveMarketScenario.TIME_TO_BID = 5;
                        ChatUtils.sendAllScenarioMessage("&a" + slavesLeft.get(0).getName() + " &bcan now be bid on!", slaveMarketScenario);
                        ChatUtils.sendAllScenarioMessage("&bYou have 10 seconds to initialize a bid!", slaveMarketScenario);
                        slaveMarketScenario.initializeTask = new BukkitRunnable() {
                            int time = 10;

                            @Override
                            public void run() {
                                if (time == 0) {
                                    slaveMarketScenario.leftOverTeam.addMember(PacketUtils.getNameFromUUID(slaveMarketScenario.CURRENT_SLAVE));


                                    //Flip to next guy
                                    slavesLeft.remove(UHCPlayer.get(slaveMarketScenario.CURRENT_SLAVE));
                                    slaveMarketScenario.PLAYER_INDEX++;
                                    slaveMarketScenario.CURRENT_SLAVE = (slavesLeft.get(slaveMarketScenario.PLAYER_INDEX)).getUuid();
                                    slaveMarketScenario.TIME_TO_BID = 5;
                                    cancel();
                                    return;
                                }
                            }
                        }.runTaskTimer(uhc, 0, 20);

                    } else {
                        //Nobody bid

                    }
                }


            }
        }.runTaskTimer(uhc, 0, 20);


//            new BukkitRunnable() {
//
//                @Override
//                public void run() {
//                    slaveMarketScenario.TIME_TO_BID--;
//                    if (slaveMarketScenario.TIME_TO_BID == 0) {
//                        UUID onwerUUID = slaveMarketScenario.CURRENT_TOP_BIDDER;
//                        if (owner == null) {
//                            ChatUtils.sendAll("&cReally guys? No one bid? Guess it goes to team one.");
//                            Team targetTeam = TeamManager.getInstance().getTeam("UHC1");
//                            targetTeam.addMember(SlaveOwnerCommand.getSlave());
//
//                        }
//                        Team targetTeam = TeamManager.getInstance().getTeambyPlayerOnTeam(SlaveOwnerCommand.getTopBidder().getName());
//                        targetTeam.addMember(SlaveOwnerCommand.getSlave());
//                        ChatUtils.sendAllScenarioMessage("&c" + SlaveOwnerCommand.getSlave().getName() + " has been sold to " + SlaveOwnerCommand.getTopBidder() + "!", slaveMarketScenario);
//                        int diamonds = slaveMarketScenario.owners.get(SlaveOwnerCommand.getTopBidder().getUniqueId());
//                        slaveMarketScenario.owners.replace(SlaveOwnerCommand.getTopBidder().getUniqueId(), diamonds - currentBid);
//                        slaveMarketScenario.getTopBidder().getInventory().removeItem(new ItemStack(Material.DIAMOND, currentBid));
//                        slaveMarketScenario.setSlave(slaves.get(player));
//                        player++;
//
//
//                        TIME_TO_BID = 5;
//
//
//                    } else {
//                        ChatUtils.sendAll(SlaveOwnerCommand.getSlave().getName() + " Will be sold to " + SlaveOwnerCommand.getTopBidder().getName() + " in " + TIME_TO_BID + "!");
//
//                    }
//                }
//            }.runTaskTimer(UHC.get(), 0, 20);
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
        return false;
    }
}