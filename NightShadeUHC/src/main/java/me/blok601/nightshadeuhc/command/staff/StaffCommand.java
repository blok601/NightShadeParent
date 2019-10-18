package me.blok601.nightshadeuhc.command.staff;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.command.UHCCommand;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 6/19/2017.
 */
public class StaffCommand implements UHCCommand {


    @Override
    public String[] getNames() {
        return new String[]{
                "staff"
        };
    }

    @Override
    public void onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player p = (Player) commandSender;
        UHCPlayer uhcPlayer = UHCPlayer.get(p);
        if (uhcPlayer.isStaffMode()) {
            uhcPlayer.unspec();
            Bukkit.getOnlinePlayers().forEach(o -> o.showPlayer(p));
            p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
            uhcPlayer.setStaffMode(false);
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);
            p.chat("/rea");
            //UHC.players.add(p.getUniqueId());
            p.sendMessage(ChatUtils.message("&eYou are no longer in staff mode!"));
        } else {
            uhcPlayer.staffMode();
        }
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
