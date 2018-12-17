package me.blok601.nightshadeuhc.scenario.cmd.mole;

import com.nightshadepvp.core.Rank;
import com.nightshadepvp.core.entity.NSPlayer;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.scenario.MolesScenario;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Blok on 7/6/2018.
 */
public class MolesCommand implements UHCCommand{
    @Override
    public String[] getNames() {
        return new String[]{
                "moles"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        UHCPlayer gamePlayer = UHCPlayer.get(p.getUniqueId());
        if(gamePlayer == null){
            p.sendMessage(ChatUtils.message("&cThere was a problem executing this command!"));
            return;
        }

        if(MolesScenario.moles.isEmpty()){
            p.sendMessage(ChatUtils.message("&cMoles have not been set!"));
            return;
        }

        if(!MolesScenario.moles.containsKey(p.getUniqueId()) && !gamePlayer.isSpectator()){
            p.sendMessage(ChatUtils.message("&cYou are not a mole!"));
            return;
        }

        if (gamePlayer.isSpectator()) {
            if (!NSPlayer.get(p).hasRank(Rank.TRIAL)) {
                p.sendMessage(ChatUtils.message("&cYou must be playing the game to view the moles!"));
                return;
            }
        }

        if (gamePlayer.isSpectator() && GameManager.get().getHost().getUniqueId() != p.getUniqueId()) {
            p.sendMessage(ChatUtils.message("&cYou must be the host to view the moles!"));
            return;
        }

        p.sendMessage(ChatUtils.message("&eThe following moles:"));
            p.sendMessage(ChatUtils.format("&5&m-----------------------------------"));
            Player mole;
            OfflinePlayer offlineMole;
            for (UUID uuid : MolesScenario.moles.keySet()){
                mole = Bukkit.getPlayer(uuid);
                if(mole != null){
                    p.sendMessage(ChatUtils.format("&e- &a" + mole.getName()));
                }else{
                    offlineMole = Bukkit.getOfflinePlayer(uuid);
                    p.sendMessage(ChatUtils.format("&e- &7" + offlineMole.getName()));
                }
            }
            p.sendMessage(ChatUtils.format("&5&m-----------------------------------"));
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
