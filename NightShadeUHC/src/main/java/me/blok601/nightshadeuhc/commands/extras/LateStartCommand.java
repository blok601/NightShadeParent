package me.blok601.nightshadeuhc.commands.extras;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.commands.UHCCommand;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.utils.ScatterUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Blok on 12/9/2017.
 */
public class LateStartCommand implements UHCCommand{
    @Override
    public String[] getNames() {
        return new String[]{
                "latestart"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if(args.length != 1){
            p.sendMessage(ChatUtils.message("&cUsage: /latestart <player>"));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            p.sendMessage(ChatUtils.message("&cThat player couldn't be found!"));
            return;
        }

        if(UHC.players.contains(target.getUniqueId())){
            p.sendMessage(ChatUtils.message("&cThat player is already in the game!"));
            return;
        }

        UHC.players.add(target.getUniqueId());
        target.getInventory().clear();
        target.getInventory().setArmorContents(null);
        target.setLevel(0);
        target.setExp(0F);
        target.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 10));
        ScatterUtil.scatterPlayer(GameManager.getWorld(), (int) GameManager.getBorderSize(), target);
        target.playSound(target.getLocation(), Sound.CHICKEN_EGG_POP, 5, 5);
        p.sendMessage(ChatUtils.message("&aYou have scattered &e" + target.getName()));
        target.sendMessage(ChatUtils.message("&eYou were scattered!"));
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.HOST;
    }

    @Override
    public boolean hasRequiredRank() {
        return true;
    }
}
