package me.blok601.nightshadeuhc.command.game.run;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.entity.object.Team;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickUnderCommand implements UHCCommand {
    @Override
    public String[] getNames() {
        return new String[]{
                "kickunder"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        UHCPlayerColl.get().getAllOnline().stream().filter(uhcPlayer -> !uhcPlayer.isSpectator()).forEach(uhcPlayer -> {

            Team team = TeamManager.getInstance().getTeam(uhcPlayer.getName());
            if (team == null) {
                GameManager.get().getWhitelist().remove(uhcPlayer.getName().toLowerCase());
                uhcPlayer.getPlayer().kickPlayer("Teams under" + args[0] + "aren't allowed in this game type!\n Join the discord for support @ \n discord.nightshadepvp.com");
                try {
                    if (team.getMembers().size() < Integer.parseInt(args[0])) {
                        GameManager.get().getWhitelist().remove(uhcPlayer.getName().toLowerCase());
                        uhcPlayer.getPlayer().kickPlayer("Teams under" + args[0] + "aren't allowed in this game type!\n Join the discord for support @ \n discord.nightshadepvp.com");
                        TeamManager.getInstance().removeTeam(team);
                    }
                }
                catch (Exception e) {
                    p.sendMessage(ChatUtils.message("You must specify a valid integer!"));
                    return;
                }
            } else {
                try {
                    if (team.getMembers().size() < Integer.parseInt(args[0])) {
                        GameManager.get().getWhitelist().remove(uhcPlayer.getName().toLowerCase());
                        uhcPlayer.getPlayer().kickPlayer("Solo's aren't allowed in this game type!\n Join the discord for support @ \n discord.nightshadepvp.com");
                        TeamManager.getInstance().removeTeam(team);
                    }
                }
                catch (Exception e) {
                    p.sendMessage(ChatUtils.message("You must specify a valid integer!"));
                    return;
                }
            }


            if (TeamManager.getInstance().getTeam(uhcPlayer.getPlayer()) == null || TeamManager.getInstance().getTeam(uhcPlayer.getName()).getMembers().size() == 1) {
                GameManager.get().getWhitelist().remove(uhcPlayer.getName().toLowerCase());
                uhcPlayer.getPlayer().kickPlayer("Solo's aren't allowed in this game type!\n Join the discord for support @ \n discord.nightshadepvp.com");
            }
        });

        p.sendMessage(ChatUtils.message("&eKicked all solos!"));
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

