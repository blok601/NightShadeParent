package me.blok601.nightshadeuhc.scenario.cmd.mole;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.scenario.MolesScenario;
import me.blok601.nightshadeuhc.entity.object.Team;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 11/17/2018.
 */
public class SetMoleCommand implements UHCCommand {
    @Override
    public String[] getNames() {
        return new String[]{
                "setmole"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        UHCPlayer uhcPlayer = UHCPlayer.get(p);
        if(!uhcPlayer.isSpectator()){
            p.sendMessage(ChatUtils.message("&cOnly the host can do that command!"));
            return;
        }

        if(!GameManager.get().getHost().getName().equalsIgnoreCase(p.getName())){
            p.sendMessage(ChatUtils.message("&cOnly the host can do that command!"));
            return;
        }

        if(args.length != 1){
            p.sendMessage(ChatUtils.message("&cUsage: /setmole <player>"));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            p.sendMessage(ChatUtils.message("&cYou can't set the mole to an offline player!"));
            return;
        }

        Team team = TeamManager.getInstance().getTeam(target);
        if(!GameManager.get().isIsTeam() || team == null){
            p.sendMessage(ChatUtils.message("&cTeams must be enabled and that player must be on a team!"));
            return;
        }

        if(MolesScenario.moles.containsKey(target.getUniqueId())){
            p.sendMessage(ChatUtils.message("&cThat player is already the mole!"));
            return;
        }

        //It should, but just in case
        MolesScenario.moles.remove(team.getMole());
        MolesScenario.moles.put(target.getUniqueId(), false);
        target.sendMessage(ChatUtils.message("&eYou are now the mole! Shhh... it's a secret."));
        target.sendMessage(ChatUtils.message("&eYou can now do /molekit to redeem the mole kit!"));
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
