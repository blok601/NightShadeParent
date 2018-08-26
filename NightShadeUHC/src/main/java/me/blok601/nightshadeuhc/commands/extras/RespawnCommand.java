package me.blok601.nightshadeuhc.commands.extras;

import com.nightshadepvp.core.Rank;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.manager.GameManager;
import me.blok601.nightshadeuhc.UHC;
import me.blok601.nightshadeuhc.utils.ChatUtils;
import me.blok601.nightshadeuhc.commands.CmdInterface;
import me.blok601.nightshadeuhc.entity.object.PlayerRespawnObject;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 12/2/2017.
 */
public class RespawnCommand implements CmdInterface{
    @Override
    public String[] getNames() {
        return new String[]{
                "respawn"
        };
    }

    @Override
    public void onCommand(CommandSender s, Command cmd, String l, String[] args) {
        Player p = (Player) s;
        if(args.length != 1){
            p.sendMessage(ChatUtils.message("&cUsage: /respawn <player>"));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            p.sendMessage(ChatUtils.message("&cThat player couldn't be found!"));
            return;
        }

        if(UHC.players.contains(target.getUniqueId())){
            p.sendMessage(ChatUtils.message("&cThat player hasn't died!"));
            return;
        }

        if(!GameManager.getInvs().containsKey(target.getUniqueId())){
            p.sendMessage(ChatUtils.message("&cThat player hasn't died yet!"));
            return;
        }

        PlayerRespawnObject obj = GameManager.getInvs().get(target.getUniqueId());
        UHCPlayer targetUHCPlayer = UHCPlayer.get(target);
        target.teleport(obj.getLocation());
        target.getInventory().setArmorContents(obj.getArmor());
        target.getInventory().setContents(obj.getItems());
        if(targetUHCPlayer.isSpectator()){
            targetUHCPlayer.unspec();
        }

        if(targetUHCPlayer.isStaffMode()){
            Bukkit.getOnlinePlayers().forEach(o -> o.showPlayer(p));
            p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
            targetUHCPlayer.setStaffMode(false);
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);
            p.chat("/rea");
        }

        if(targetUHCPlayer.isVanished()) targetUHCPlayer.unVanish();

        target.setGameMode(GameMode.SURVIVAL);

        GameManager.getInvs().remove(target.getUniqueId());

        target.sendMessage(ChatUtils.message("&eYou have been respawned by &a" + p.getName()));
        p.sendMessage(ChatUtils.message("&eYou have respawned &a" + target.getName()));

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
