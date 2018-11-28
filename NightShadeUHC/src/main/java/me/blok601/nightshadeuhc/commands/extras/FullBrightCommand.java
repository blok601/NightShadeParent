package me.blok601.nightshadeuhc.commands.extras;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Blok on 4/25/2018.
 */
public class FullBrightCommand implements UHCCommand{
    @Override
    public String[] getNames() {
        return new String[]{
                "fullbright"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if(p.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
            p.removePotionEffect(PotionEffectType.NIGHT_VISION);
            p.sendMessage(ChatUtils.message("&eYou have removed your night vision!"));
            return;
        }

        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 4));
        p.sendMessage(ChatUtils.message("&eYou have enabled Night Vision!"));

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
