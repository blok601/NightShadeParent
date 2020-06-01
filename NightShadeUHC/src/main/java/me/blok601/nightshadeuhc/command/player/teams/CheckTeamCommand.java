package me.blok601.nightshadeuhc.command.player.teams;

import com.google.common.base.Joiner;
import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.object.Team;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.scenario.Scenario;
import me.blok601.nightshadeuhc.scenario.ScenarioManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckTeamCommand implements UHCCommand {

    private ScenarioManager scenarioManager;

    public CheckTeamCommand(ScenarioManager scenarioManager) {
        this.scenarioManager = scenarioManager;
    }

    @Override
    public String[] getNames() {
        return new String[]{
                "checkteam"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        UHCPlayer uhcPlayer = UHCPlayer.get(s);
        if (!GameManager.get().isIsTeam()) {
            uhcPlayer.msg(ChatUtils.message("&cIt is not a teams game!"));
            return;
        }

        if (args.length > 1) {
            uhcPlayer.msg(ChatUtils.message("&cUsage: /checkteam [player]"));
            return;
        }

        if (args.length == 0) {
            //Self
            Team t = TeamManager.getInstance().getTeam(uhcPlayer.getPlayer());
            if (t == null) {
                uhcPlayer.msg(ChatUtils.message("&cYou are not on a team!"));
                return;
            }
            uhcPlayer.msg(ChatUtils.format("&b&m---------------------------------"));
            uhcPlayer.msg(ChatUtils.format("&aYour Team&8: &e" + Joiner.on("&7, &e").join(t.getMembers())));
            uhcPlayer.msg(ChatUtils.format("&b&m---------------------------------"));
        } else {
            Scenario scenario = scenarioManager.getScen("Secret Teams");
            if (scenario != null && scenario.isEnabled()) {
                if(!NSPlayer.get(s).hasRank(Rank.TRIAL) && !uhcPlayer.isSpectator()){
                    uhcPlayer.msg(ChatUtils.format(scenario.getPrefix() + "&cYou can't view other player's teams in Secret Teams!"));
                    return;
                }
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                Team targetTeam = TeamManager.getInstance().getTeambyPlayerOnTeam(args[1]);
                if (targetTeam == null) {
                    uhcPlayer.msg(ChatUtils.message("&cThat player is not on a team!"));
                    return;
                }

                uhcPlayer.msg(ChatUtils.format("&b&m---------------------------------"));
                uhcPlayer.msg(ChatUtils.format("&a" + args[1] + "'s Team&8: &e" + Joiner.on("&7, &e").join(targetTeam.getMembers())));
                uhcPlayer.msg(ChatUtils.format("&b&m---------------------------------"));

            } else {
                Team targetTeam = TeamManager.getInstance().getTeam(target);
                if (targetTeam == null) {
                    uhcPlayer.msg(ChatUtils.message("&cThat player is not on a team!"));
                    return;
                }


                uhcPlayer.msg(ChatUtils.format("&b&m---------------------------------"));
                uhcPlayer.msg(ChatUtils.format("&a" + args[1] + "'s Team&8: " + Joiner.on("&7, &e").join(targetTeam.getMembers())));
                uhcPlayer.msg(ChatUtils.format("&b&m---------------------------------"));
            }
        }
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
