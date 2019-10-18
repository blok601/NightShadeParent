package me.blok601.nightshadeuhc.scenario.cmd.superhero;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayerColl;
import me.blok601.nightshadeuhc.scenario.SuperheroesScenario;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class ResetPowersCommand implements UHCCommand {
    
    @Override
    public String[] getNames() {
        return new String[]{
                "resetpowers"
        };
    }
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        UHCPlayerColl.get().getAllPlaying().forEach(uhcPlayer -> {
            Player player = uhcPlayer.getPlayer();
            for (PotionEffect effect : player.getActivePotionEffects())

                player.removePotionEffect(effect.getType());
            SuperheroesScenario.powers.remove(player.getUniqueId());


        });
        ChatUtils.sendAll("Super Powers have been Reset!");
    }



    @Override
    public boolean playerOnly() {
        return false;
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

