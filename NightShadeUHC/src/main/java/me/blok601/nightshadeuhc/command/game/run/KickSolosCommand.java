package me.blok601.nightshadeuhc.command.game.run;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.manager.TeamManager;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 8/22/2018.
 */
public class KickSolosCommand implements UHCCommand {

    @Override
    public String[] getNames() {
        return new String[]{
                "kicksolos"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        UHCPlayerColl.get().getAllOnline().stream().filter(uhcPlayer -> !uhcPlayer.isSpectator()).forEach(uhcPlayer -> {
            if(TeamManager.getInstance().getTeam(uhcPlayer.getPlayer()) == null){
                GameManager.get().getWhitelist().remove(uhcPlayer.getName().toLowerCase());
                uhcPlayer.getPlayer().kickPlayer("Solo's aren't allowed in this game type!\n Join the discord for support @ \n discord.me/NightShadeMC");
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
