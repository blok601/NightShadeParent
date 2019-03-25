package me.blok601.nightshadeuhc.scenario.cmd.SlaveMarket;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
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
    public static int time = 5;
    public static int currentBid = 0;
    int player;
    public boolean done = false;
    ArrayList<Player> slaves = new ArrayList<Player>();

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
            p.sendMessage(ChatUtils.message("&cOnly the host can do that command!"));
            return;
        }

        if (!GameManager.get().getHost().getName().equalsIgnoreCase(p.getName())) {
            p.sendMessage(ChatUtils.message("&cOnly the host can do that command!"));
            return;
        }
        SlaveMarketScenario.owners.forEach((k, v) -> {

            Player o = Bukkit.getPlayer(k);
            o.getInventory().addItem(new ItemStack(Material.DIAMOND, v));
        });

        UHCPlayerColl.get().getAllPlaying().forEach(player -> {
            if (!SlaveMarketScenario.owners.containsKey(player.getUuid())) {
                slaves.add((Player)player);
            }



        });
        SlaveOwnerCommand.setSlave(slaves.get(0));



            new BukkitRunnable() {

                @Override
                public void run() {
                    time--;
                    if (time == 0) {
                        Player owner = SlaveOwnerCommand.getTopBidder();
                        if (owner == null) {
                            ChatUtils.sendAll("&cReally guys? No one bid? Guess it goes to team one.");
                            Team targetTeam = TeamManager.getInstance().getTeam("UHC1");
                            targetTeam.addMember(SlaveOwnerCommand.getSlave());

                        }
                        Team targetTeam = TeamManager.getInstance().getTeambyPlayerOnTeam(SlaveOwnerCommand.getTopBidder().getName());
                        targetTeam.addMember(SlaveOwnerCommand.getSlave());
                        ChatUtils.sendAll("&c" + SlaveOwnerCommand.getSlave().getName() + " has been sold to " + SlaveOwnerCommand.getTopBidder() + "!");
                        int diamonds = SlaveMarketScenario.owners.get(SlaveOwnerCommand.getTopBidder().getUniqueId());
                        SlaveMarketScenario.owners.replace(SlaveOwnerCommand.getTopBidder().getUniqueId(), diamonds - currentBid);
                        SlaveOwnerCommand.getTopBidder().getInventory().removeItem(new ItemStack(Material.DIAMOND, currentBid));
                        SlaveOwnerCommand.setSlave(slaves.get(player));
                        player++;


                        time = 5;


                    } else {
                        Bukkit.broadcastMessage(ChatUtils.message(SlaveOwnerCommand.getSlave().getName() + " Will be sold to " + SlaveOwnerCommand.getTopBidder().getName() + " in " + time + "!"));

                    }
                }
            }.runTaskTimer(UHC.get(), 0, 20);
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