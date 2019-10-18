package me.blok601.nightshadeuhc.command.player;

import com.google.common.base.Joiner;
import com.massivecraft.massivecore.store.SenderEntity;
import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.entity.object.GameState;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Blok on 9/4/2018.
 */
public class AliveCommand implements UHCCommand {
    @Override
    public String[] getNames() {
        return new String[]{
                "alive"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if(!GameState.gameHasStarted()){
            p.sendMessage(ChatUtils.message("&cThe game hasn't started!"));
            return;
        }

        HashSet<String> online = UHCPlayerColl.get().getAllPlaying().stream().map(SenderEntity::getName).collect(Collectors.toCollection(HashSet::new));
        HashSet<String> offline = new HashSet<>();
        UHCPlayer uhcPlayer;
        for (UUID u : UHC.loggedOutPlayers) {
            uhcPlayer = UHCPlayer.get(u);
            if (uhcPlayer != null) {
                offline.add(uhcPlayer.getName());
            }
        }
        String on = Joiner.on("&7, &a").join(online);
        String off = Joiner.on("&7, &7").join(offline);

        p.sendMessage(ChatUtils.message("&eAlive Players: &a" + on + " &7" + off));
    }

    @Override
    public boolean playerOnly() {
        return true;
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
