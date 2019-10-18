package me.blok601.nightshadeuhc.command.player.disguise;

import com.nightshadepvp.core.entity.NSPlayer;
import de.robingrether.idisguise.api.UndisguiseEvent;
import de.robingrether.idisguise.disguise.DisguiseType;
import me.blok601.nightshadeuhc.entity.UHCPlayer;
import me.blok601.nightshadeuhc.util.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by Blok on 7/20/2017.
 */
public class Undisguise implements Listener{

    @EventHandler
    public void unDis(UndisguiseEvent e){
        Player p = e.getPlayer();
        UHCPlayer gamePlayer = UHCPlayer.get(p.getUniqueId());
        NSPlayer u = NSPlayer.get(p.getUniqueId());

        if(e.getDisguise().getType() == DisguiseType.PLAYER){
            if(gamePlayer.isDisguised()){

                DisguiseObject object = RandomDisguiseCommand.getDis().get(p.getUniqueId());


                gamePlayer.setDisguised(false);
                u.setRank(object.getPreRank());
                if(object.isOp()){
                    p.setOp(true);
                    p.sendMessage(ChatUtils.message("&aYou are now op again!"));
                }
            }
        }

    }
}
